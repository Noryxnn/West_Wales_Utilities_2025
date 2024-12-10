CREATE DATABASE IF NOT EXISTS client_project_db;
USE client_project_db;

DROP TABLE IF EXISTS request_status, requests, visits_archive, visits, locations, locations_archive, location_types, user_roles, roles, users;

-- create the tables in the correct order
CREATE TABLE IF NOT EXISTS users
(
    user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255),
    password     VARCHAR(255),
    email        VARCHAR(255) NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS roles
(
    role_id   INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS user_roles
(
    user_role_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT,
    role_id      INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS location_types
(
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

SELECT *
FROM location_types;

CREATE TABLE IF NOT EXISTS locations
(
    location_id    INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    city           VARCHAR(255) NOT NULL,
    postcode       VARCHAR(255) NOT NULL,
    type_id        INT,
    deleted        BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (type_id) REFERENCES location_types (type_id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS locations_archive
(
    location_id    INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    city           VARCHAR(255) NOT NULL,
    postcode       VARCHAR(255) NOT NULL,
    type_id        INT,
    FOREIGN KEY (type_id) REFERENCES location_types (type_id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS requests
(
    request_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT,
    request_date     DATETIME    NOT NULL,
    visit_start_date DATE        NOT NULL,
    visit_end_date   DATE        NOT NULL,
    status           VARCHAR(20) NOT NULL DEFAULT 'PENDING'
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS visits
(
    visit_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id            BIGINT   NOT NULL,
    location_id        INT      NOT NULL,
    check_in_datetime  DATETIME NOT NULL,
    check_out_datetime DATETIME
) ENGINE = InnoDB;


