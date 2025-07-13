--
-- MySQL database dump
--

-- Started on 2025-06-29 15::58

SET max_execution_time = 0;

CREATE TABLE tile (
    tile_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,

    -- Auditable fields
    created_date DATETIME NOT NULL,
    modified_date DATETIME,
    created_by_id BIGINT,
    modified_by_id BIGINT,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO tile (tile_id, name, title, created_date, modified_date, created_by_id, modified_by_id, created_by, modified_by, is_deleted) VALUES 
(1, 'MASTER', 'Master', NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(2, 'EMPLOYEE', 'Employee', NOW(), NOW(), -1, -1, 'System', 'System', FALSE);