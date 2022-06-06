

class CVETweet:
    def __init__(self, tweet_id, tweet_text, poc_flag, cves_mentioned, date, user_id):
        self.tweet_id = tweet_id
        self.tweet_text = tweet_text
        self.poc_flag = poc_flag
        self.cves_mentioned = cves_mentioned
        self.date = date
        self.user_id = user_id

    def __str__(self):
        return '%s(%s)' % (type(self).__name__,', '.join('%s=%s' % item for item in sorted(vars(self).items())))
