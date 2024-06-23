--
-- MySQL database dump
--

-- Started on 2024-04-20 11:15:58

SET max_execution_time = 0;

CREATE TABLE ca_user (
    organization_id VARCHAR(100),
    group_id VARCHAR(100),
    sub_group_id VARCHAR(100),
    user_id VARCHAR(100),
    external_user_id VARCHAR(100),
    mobile_no VARCHAR(13),
    alt_mobile VARCHAR(13),
    email VARCHAR(50),
    password VARCHAR(50),
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    resource JSON,
    is_active BOOLEAN,
    created_date DATETIME,
    modified_date DATETIME,
    PRIMARY KEY (organization_id, group_id, sub_group_id, user_id),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (organization_id, group_id, sub_group_id) REFERENCES ca_sub_group(organization_id, group_id, sub_group_id)
);