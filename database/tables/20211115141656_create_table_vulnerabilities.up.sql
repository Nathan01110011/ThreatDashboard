CREATE TABLE vulnerabilities (
  vulnerability_id varchar(50) PRIMARY KEY,
  vulnerability_title varchar(1000) DEFAULT NULL,
  vulnerability_cwe varchar(50) DEFAULT NULL,
  vulnerability_date_published date DEFAULT NULL,
  vulnerability_date_updated date DEFAULT NULL,
  vulnerability_cpe text DEFAULT NULL,
  cvss_base_score decimal DEFAULT NULL,
  priority int DEFAULT NULL
)