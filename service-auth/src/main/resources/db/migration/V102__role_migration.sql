--
-- MySQL database dump
--

-- Started on 2025-06-29 15::58

SET max_execution_time = 0;

CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_scope_id INT NOT NULL,
    organization_id INT NOT NULL DEFAULT 0,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,

    -- Auditable fields
    created_date DATETIME NOT NULL,
    modified_date DATETIME,
    created_by_id BIGINT,
    modified_by_id BIGINT,
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (role_scope_id) REFERENCES role_scope(role_scope_id)
);

CREATE TABLE permission (
    permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    resource_name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
);

INSERT INTO role (
    role_id, role_scope_id, organization_id, name, title, description, is_default, created_date, modified_date, created_by_id, modified_by_id, created_by, modified_by, is_deleted 
) VALUES
(1, 1, 1, 'ADMINISTRATOR', 'Administrator', 'Full portal-wide access, including control panel', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(2, 1, 1, 'POWERUSER', 'Power User', 'Portal-wide role, typically for privileged users', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(3, 1, 1, 'USER', 'User', 'Default role for all registered users', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(4, 2, 1, 'MEMBER', 'Member', 'Standard site member — can view content', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(5, 2, 1, 'ADMINISTRATOR', 'Administrator', 'Manages an organization’s users and content', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE),
(6, 2, 1, 'OWNER', 'Owner', 'Full control over an organization', FALSE, NOW(), NOW(), -1, -1, 'System', 'System', FALSE);

INSERT INTO permission (
    permission_id, role_id, name, resource_name, title, description
) VALUES
(1, 1, 'VIEW', 'Documents and Media', 'View', 'Document & Media View only'),
(2, 1, 'DELETE', 'Documents and Media', 'Delete', 'Document & Media View Delete'),
(3, 1, 'EDIT', 'Documents and Media', 'Edit', 'Document & Media View Edit');
