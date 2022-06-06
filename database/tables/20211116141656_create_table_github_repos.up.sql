CREATE TABLE github_repos (
  repo_id int PRIMARY KEY,
  repo_name varchar(50) DEFAULT NULL,
  date_created date DEFAULT NULL,
  date_updated date DEFAULT NULL,
  stars int DEFAULT NULL,
  url varchar(200) DEFAULT NULL
)