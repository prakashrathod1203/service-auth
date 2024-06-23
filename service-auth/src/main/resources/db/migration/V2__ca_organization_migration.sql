--
-- MySQL database dump
--

-- Started on 2024-06-22 11:15:58

SET max_execution_time = 0;

CREATE TABLE ca_organization (
     organization_id VARCHAR(100) PRIMARY KEY,
     display_name VARCHAR(255),
     description TEXT,
     address TEXT,
     email VARCHAR(50),
     contact_number VARCHAR(13),
     created_date DATETIME,
     modified_date DATETIME
);


