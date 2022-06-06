
def query_get_latest_saved_date():
    return "SELECT MAX(date_created) FROM cve_tweets LIMIT 1"


def query_insert_new_tweets_into_cve_tweets(tweet):
    cves_mentioned = str(tweet.cves_mentioned).replace("'", "''")
    query = "INSERT INTO cve_tweets (tweet_id, tweet_text, poc_flag, cves_mentioned, date_created, user_id)" \
            f" VALUES ({tweet.tweet_id},'{str(tweet.tweet_text)}',{tweet.poc_flag},'{cves_mentioned}'" \
            f",'{tweet.date}',{tweet.user_id});"
    return query


def query_get_tweets_from_day(date):
    return "SELECT * FROM cve_tweets WHERE DATE(date_created) = '{}';".format(date)


def query_get_twitter_poc_mentions(date):
    return f"SELECT * FROM cve_tweets WHERE poc_flag = true AND DATE(date_created) = '{date}';"


def query_get_status_of_vuln_in_incapsush(cve_code):
    query = 'SELECT vulnerability_status, INCP_vulnerability_status, id FROM vulnerabilities ' \
            f'WHERE Vulnerability_id = "{cve_code}";'
    return query


def query_get_total_num_of_cve_mentions(date):
    query = f'SELECT cves_mentioned, count(*) FROM cve_tweets ct WHERE date_created > "{date}" ' \
            f'GROUP BY cves_mentioned ORDER BY count(*) DESC;'
    return query


def query_get_total_num_of_cve_mentions_with_date(date):
    query = f'SELECT cves_mentioned, cast(date_created as DATE) as date_tweet FROM cve_tweets ct ' \
            f'WHERE date_created > "{date}" GROUP BY cves_mentioned, date_tweet ORDER BY cves_mentioned DESC;'
    return query


def query_get_tweets_since_day(date):
    return "SELECT * FROM cve_tweets WHERE DATE(date_created) > '{}';".format(date)


def query_get_daily_tweet_threshold():
    return "SELECT value FROM cve_tweets_thresholds WHERE threshold_id = 'daily_total';"


def query_get_cve_data(cve_id):
    return "SELECT * FROM vulnerabilities WHERE Vulnerability_id = '{}';".format(cve_id)


def query_update_cve_priority(cve_id, day):
    query = "UPDATE vulnerabilities SET priority = 3, Vulnerability_date_updated = '{}'" \
           " WHERE Vulnerability_id = '{}';".format(day, cve_id)
    return query


def query_insert_audit(cve_id):
    comment = "Priority updated, exceeded daily tweet threshold"
    query = "INSERT INTO vulns.vulnerability_audit(vulnerability_id,comment) values ('{}','{}');".format(cve_id, comment)
    return query
