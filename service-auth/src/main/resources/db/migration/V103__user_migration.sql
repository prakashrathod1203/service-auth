--
-- MySQL database dump
--

-- Started on 2025-06-29 15::58

SET max_execution_time = 0;

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(255) NOT NULL UNIQUE,
    organization_id INT NOT NULL DEFAULT 0,
    external_user_id VARCHAR(255),
    resource JSON,
    phone VARCHAR(16) NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    login_attempts INT DEFAULT 0,
    last_login_at DATETIME,

    -- Auditable fields
    created_date DATETIME NOT NULL,
    modified_date DATETIME,
    created_by_id BIGINT,
    modified_by_id BIGINT,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_role_mapping (
    user_role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    UNIQUE (user_id, role_id)
);