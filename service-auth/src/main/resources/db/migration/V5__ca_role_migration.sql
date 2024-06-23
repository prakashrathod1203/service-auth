--
-- MySQL database dump
--

-- Started on 2024-06-22 11:15:58

SET max_execution_time = 0;

CREATE TABLE ca_role (
     organization_id VARCHAR(100),
     group_id VARCHAR(100),
     sub_group_id VARCHAR(100),
     role_id VARCHAR(100),
     display_name VARCHAR(255),
     description TEXT,
     created_date DATETIME,
     modified_date DATETIME,
     PRIMARY KEY (organization_id, group_id, sub_group_id, role_id),
     INDEX idx_role_id (role_id),
     FOREIGN KEY (organization_id, group_id, sub_group_id) REFERENCES ca_sub_group(organization_id, group_id, sub_group_id)
);


