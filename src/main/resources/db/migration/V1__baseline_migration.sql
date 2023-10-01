--
-- MySQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- CREATE SCHEMA ${dbuser};

SET default_tablespace = '';

SET default_table_access_method = heap;

-- ca_organization definition

CREATE TABLE "${dbuser}".ca_organization (
  `id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_group definition

CREATE TABLE "${dbuser}".ca_group (
  `id` varchar(20) NOT NULL,
  `org_id` varchar(10) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_sub_group definition

CREATE TABLE "${dbuser}".ca_sub_group (
  `id` varchar(20) NOT NULL,
  `group_id` varchar(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_role definition

CREATE TABLE "${dbuser}".ca_role (
  `id` varchar(100) NOT NULL,
  `group_id` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_user definition

CREATE TABLE "${dbuser}".ca_user (
  `login_id` varchar(100) NOT NULL,
  `external_user_id` varchar(100) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(75) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `middle_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `resource` json DEFAULT NULL,
  `is_active` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_user_role definition

CREATE TABLE "${dbuser}".ca_user_role (
  `role_id` varchar(50) NOT NULL,
  `login_id` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`,`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ca_user_sub_group definition

CREATE TABLE "${dbuser}".ca_user_sub_group (
  `sub_group_id` varchar(100) NOT NULL,
  `login_id` varchar(100) NOT NULL,
  PRIMARY KEY (`sub_group_id`,`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



