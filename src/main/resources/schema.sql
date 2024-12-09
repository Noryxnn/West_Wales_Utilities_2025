SET foreign_key_checks = 0;

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
                                       `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       `first_name` VARCHAR(255) NOT NULL,
                                       `last_name` VARCHAR(255),
                                       `email` VARCHAR(255) NOT NULL,
                                       `company_name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
                                       `role_id` INT AUTO_INCREMENT PRIMARY KEY,
                                       `role_name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
                                            `user_role_id` INT AUTO_INCREMENT PRIMARY KEY,
                                            `user_id` INT,
                                            `role_id` INT
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `location_types`;
CREATE TABLE IF NOT EXISTS `location_types` (
                                                `type_id` INT AUTO_INCREMENT PRIMARY KEY,
                                                `name` VARCHAR(255)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `locations`;
CREATE TABLE IF NOT EXISTS `locations` (
                                           `location_id` INT AUTO_INCREMENT PRIMARY KEY,
                                           `name` VARCHAR(255) NOT NULL,
                                           `address_line_1` VARCHAR(255) NOT NULL,
                                           `address_line_2` VARCHAR(255),
                                           `city` VARCHAR(20) NOT NULL,
                                           `postcode` VARCHAR(255) NOT NULL,
                                           `type_id` INT,
                                           `deleted` BOOLEAN DEFAULT FALSE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS locations_archive;
CREATE TABLE IF NOT EXISTS locations_archive (
                                                 `location_id` INT AUTO_INCREMENT PRIMARY KEY,
                                                 `name` VARCHAR(255) NOT NULL,
                                                 `address_line_1` VARCHAR(255) NOT NULL,
                                                 `address_line_2` VARCHAR(255),
                                                 `city` VARCHAR(20) NOT NULL,
                                                 `postcode` VARCHAR(255) NOT NULL,
                                                 `type_id` INT,
                                                 `deleted_at` timestamp
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `visits`;
CREATE TABLE IF NOT EXISTS `visits` (
                                        `visit_id` INT AUTO_INCREMENT PRIMARY KEY,
                                        `location_id` INT,
                                        `user_id` INT,
                                        `check_in` TIMESTAMP NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `requests`;
CREATE TABLE IF NOT EXISTS `requests` (
                                          `request_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          `user_id` BIGINT,
                                          `request_date` DATETIME NOT NULL,
                                          `visit_start_date` DATE NOT NULL,
                                          `visit_end_date` DATE NOT NULL,
                                          `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING'
) ENGINE=InnoDB;

