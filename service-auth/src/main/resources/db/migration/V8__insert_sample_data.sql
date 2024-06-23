--
-- MySQL database dump
--

-- Started on 2024-04-20 11:15:58

SET max_execution_time = 0;

-- Organization
INSERT INTO ca_organization (organization_id, display_name, description, address, email, contact_number,created_date, modified_date) VALUES
('SARTHEE', 'Tech Sarthee', 'Company provides consultancy.', 'Coming soon...', 'sarthee.tech@gmail.com', '1234567890', now(), now());

-- Group
INSERT INTO ca_group (organization_id, group_id, display_name, description,created_date, modified_date) VALUES
('SARTHEE', 'SERVICE', 'Tech Services', 'Tech Services', now(), now());

-- Sub Group
INSERT INTO ca_sub_group (organization_id, group_id, sub_group_id, display_name, identity_code, description, address, email, contact_number, created_date, modified_date) VALUES
('SARTHEE', 'SERVICE', 'QR_CODE', 'QR Code Service', '', 'QR Code Service', 'Coming soon...', 'sarthee.tech@gmail.com', '0987654321', now(), now());

-- Role
INSERT INTO ca_role (organization_id, group_id, sub_group_id, role_id, display_name, description, created_date, modified_date) VALUES
('SARTHEE', 'SERVICE', 'QR_CODE', 'USER', 'User Role', 'User Role', now(), now()),
('SARTHEE', 'SERVICE', 'QR_CODE', 'ADMIN', 'Admin Role', 'Admin Role', now(), now());

-- User
INSERT INTO ca_user (organization_id, group_id, sub_group_id, user_id, external_user_id, mobile_no, alt_mobile, email, password, first_name, middle_name, last_name, resource, is_active, created_date, modified_date) VALUES
('SARTHEE', 'SERVICE', 'QR_CODE', '8140811011', '', '8140811011', '8140811517', 'mca.amitrami@gmail.com', 'password', 'Amit', '', 'Rami', '{}', TRUE, now(), now()),
('SARTHEE', 'SERVICE', 'QR_CODE', '8140811010', '', '8140811010', '8140811516', 'mca.prakashrathod@gmail.com', 'password', 'Prakash', '', 'Rathod', '{}', TRUE, now(), now());

-- User & Role Mapping
INSERT INTO ca_user_role (user_id, role_id, organization_id, group_id, sub_group_id) VALUES
('8140811011', 'ADMIN', 'SARTHEE', 'SERVICE', 'QR_CODE'),
('8140811010', 'USER', 'SARTHEE', 'SERVICE', 'QR_CODE');