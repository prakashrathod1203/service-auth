--
-- MySQL database dump
--

-- Started on 2024-06-22 11:15:58

SET max_execution_time = 0;

CREATE TABLE ca_group (
     organization_id VARCHAR(100),
     group_id VARCHAR(100),
     display_name VARCHAR(255),
     description TEXT,
     created_date DATETIME,
     modified_date DATETIME,
     PRIMARY KEY (organization_id, group_id),
     FOREIGN KEY (organization_id) REFERENCES ca_organization(organization_id)
);


