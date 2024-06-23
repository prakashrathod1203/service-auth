--
-- MySQL database dump
--

-- Started on 2024-04-20 11:15:58

SET max_execution_time = 0;

CREATE TABLE ca_user_role (
    user_id VARCHAR(100),
    role_id VARCHAR(100),
    organization_id VARCHAR(100),
    group_id VARCHAR(100),
    sub_group_id VARCHAR(100),
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id, organization_id, group_id, sub_group_id) REFERENCES ca_user(user_id, organization_id, group_id, sub_group_id),
    FOREIGN KEY (role_id, organization_id, group_id, sub_group_id) REFERENCES ca_role(role_id, organization_id, group_id, sub_group_id)
);

