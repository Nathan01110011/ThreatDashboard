CREATE TABLE cve_tweets (
  tweet_id bigint PRIMARY KEY,
  tweet_text varchar(500) DEFAULT NULL,
  poc_flag boolean NOT NULL DEFAULT false,
  cves_mentioned varchar(200) DEFAULT NULL,
  date_created timestamptz NOT NULL,
  user_id bigint NOT NULL
)