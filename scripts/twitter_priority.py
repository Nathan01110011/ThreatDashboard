import re
import logging
import twint
import datetime
from github import Github

import Utils
import twitter_queries
import github_queries
from CVETweet import CVETweet


def start_vulnerability_data_scrape():
    logging.info("Twitter CVE Data Fetch Started")
    con = Utils.connect_to_my_sql()
    cur = con.cursor()
    github_poc_search(con, cur)
    twitter_cve_search(con, cur)
    logging.info("Twitter CVE Data Fetch Ended")


def github_poc_search(con, cur):
    today = datetime.date.today()
    last_30_days = today - datetime.timedelta(days=30)
    # Key expires in 7 days and has no real access other than searching
    g = Github("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")
    repositories = g.search_repositories(query=f'cve created:>={last_30_days} stars:>10')
    for repo in repositories:
        Utils.execute_query(github_queries.upsert_into_github_poc_table(repo), con=con, cur=cur, no_res=True)


def twitter_cve_search(con, cur):
    c = twint.Config()

    # Adding one second to the latest tweet so it is not included in search results
    since = Utils.execute_query(twitter_queries.query_get_latest_saved_date(), con=con, cur=cur)
    since_plus_one_second = since[0][0] + datetime.timedelta(0, 1)
    c.Since = since_plus_one_second.strftime("%Y-%m-%d %H:%M:%S")

    c.Search = "CVE"
    c.Store_object = True
    twint.run.Search(c)
    tweet_list = twint.output.tweets_list

    cve_list = []
    # tweet_list = setup_twitter_search(ts, last_tweet_id_saved[0][0])
    formatted_tweet_list = []
    for tweet in tweet_list:
        # – and - look the same right? They aren't, and some people use both on twitter for cves
        cve_numbers = re.findall('(CVE[–-][0-9]{4}[–-][0-9]{4,7})', getattr(tweet, 'tweet'), re.IGNORECASE)
        if cve_numbers:
            # Adding based on POC mention - possibly other keywords to be added like github/lab etc...
            poc_found = False
            cves_mentioned = []
            if re.search('(POC)', getattr(tweet, 'tweet'), re.IGNORECASE):
                poc_found = True
            for cve in cve_numbers:
                adjusted_id = cve.replace('–', '-').replace('cve', 'CVE')
                cve_list.append(adjusted_id)
                cves_mentioned.append(adjusted_id)

            date_created = getattr(tweet, 'datetime')
            escaped_text = sql_escape_tweet_text(getattr(tweet, 'tweet'))
            tweet_obj = CVETweet(getattr(tweet, 'id'), escaped_text, poc_found, cves_mentioned,
                                 date_created, getattr(tweet, 'user_id'))
            formatted_tweet_list.append(tweet_obj)

    failed_list = []

    # Inserting into table
    counter = 0
    for tweet in formatted_tweet_list:
        insert_tweet_query = twitter_queries.query_insert_new_tweets_into_cve_tweets(tweet)
        try:
            Utils.execute_query(insert_tweet_query, con=con, cur=cur, no_res=True)
            counter = counter + 1
        except Exception as e:
            failed_query = insert_tweet_query + ": Reason - " + e
            failed_list.append(failed_query)


def sql_escape_tweet_text(text):
    # I might need to revert to this stricter substitution, but the simple one works for now.
    # escaped = text.translate(str.maketrans({"-": r"\-", "]": r"\]", "\\": r"\\", "^": r"\^",
    #                                         "$": r"\$", "*": r"\*", ".": r"\."}))
    escaped = text.replace("'", "")
    escaped = escaped.replace('\\', '')
    escaped = escaped.replace('`', '')
    escaped = escaped.replace('"', '')
    escaped = escaped.rstrip("\n")
    escaped = escaped.replace('%', '')
    return escaped


if __name__ == '__main__':
    start_vulnerability_data_scrape()
