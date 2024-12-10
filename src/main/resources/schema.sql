CREATE DATABASE IF NOT EXISTS client_project_db;
USE client_project_db;

DROP TABLE IF EXISTS request_status, requests, visits_archive, visits, locations, locations_archive, location_types, user_roles, roles, users;
DROP VIEW IF EXISTS user_authorities;

-- create the tables in the correct order
CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255),
                       password VARCHAR(255),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       enabled BOOLEAN NOT NULL
) ENGINE=InnoDB;

CREATE TABLE roles (
                       role_id INT AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE user_roles (
                            user_role_id INT AUTO_INCREMENT PRIMARY KEY,
                            email VARCHAR(255),
                            role_id INT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE location_types (
                                type_id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

SELECT * FROM location_types;

CREATE TABLE locations (
                           location_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           address_line_1 VARCHAR(255) NOT NULL,
                           address_line_2 VARCHAR(255),
                           city VARCHAR(255) NOT NULL,
                           postcode VARCHAR(255) NOT NULL,
                           type_id INT,
    deleted BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB;

CREATE TABLE locations_archive (
                           location_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           address_line_1 VARCHAR(255) NOT NULL,
                           address_line_2 VARCHAR(255),
                           city VARCHAR(255) NOT NULL,
                           postcode VARCHAR(255) NOT NULL,
                           type_id INT
) ENGINE=InnoDB;

CREATE TABLE requests (
                          request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT,
                          request_date DATETIME NOT NULL,
                          visit_start_date DATE NOT NULL,
                          visit_end_date DATE NOT NULL,
                          is_approved BOOLEAN
) ENGINE=InnoDB;

CREATE TABLE visits (
                        visit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        location_id INT NOT NULL,
                        check_in_datetime DATETIME NOT NULL,
                        check_out_datetime DATETIME
) ENGINE=InnoDB;

CREATE VIEW IF NOT EXISTS user_authorities as
select u.email AS username, CONCAT('ROLE_', r.role_name) AS authority
from users u
         inner join user_roles ur on u.email = ur.email
         inner join roles r on ur.role_id = r.role_id;

SELECT * FROM user_authorities;
SELECT email AS username, password, enabled FROM users WHERE email = 'john@smith.com';
SELECT email AS username, password, enabled FROM users WHERE email = 'jane@doe.com';
SELECT email AS username, password, enabled FROM users WHERE email = 'john@doe.com'

