--
-- MySQL database dump
--

-- Started on 2024-06-22 11:15:58

SET max_execution_time = 0;

CREATE TABLE file_master (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_name VARCHAR(255) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    file_store_path VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    created_date DATETIME,
    modified_date DATETIME

);


