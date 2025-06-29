--
-- MySQL database dump
--

-- Started on 2025-06-29 15:15:58

SET max_execution_time = 0;

CREATE TABLE role_scope (
    role_scope_id INT AUTO_INCREMENT PRIMARY KEY,
    role_scope_type VARCHAR(150) NOT NULL UNIQUE,

    -- Auditable fields
    created_date DATETIME NOT NULL,
    modified_date DATETIME,
    created_by_id BIGINT,
    modified_by_id BIGINT,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO role_scope (
    role_scope_id,
    role_scope_type,
    created_date,
    modified_date,
    created_by_id,
    modified_by_id,
    created_by,
    modified_by,
    is_deleted
) VALUES
(1, 'REGULAR', NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(2, 'ORGANIZATION', NOW(), NOW(), -1, -1, 'System', 'System', FALSE);
