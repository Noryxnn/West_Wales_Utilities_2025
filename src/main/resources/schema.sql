# Temporarily disable foreign key checks to avoid errors
# Sourced from: https://www.sqlines.com/mysql/set_foreign_key_checks#:~:text=When%20to%20Use,in%20any%20parent%2Dchild%20order.
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `location_types`;
DROP TABLE IF EXISTS `locations`;
DROP TABLE IF EXISTS `visits`;
DROP TABLE IF EXISTS `visits_archive`;
DROP TABLE IF EXISTS `requests`;


CREATE TABLE IF NOT EXISTS `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255),
    `email` VARCHAR(255) NOT NULL,
    `company_name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `roles` (
    `role_id` INT AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `user_roles` (
    `user_role_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `role_id` INT,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `location_types` (
    `type_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `locations` (
    `location_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `address_line_1` VARCHAR(255) NOT NULL,
    `address_line_2` VARCHAR(255),
    `city` VARCHAR(20) NOT NULL,
    `postcode` VARCHAR(255) NOT NULL,
    `type_id` INT,
    FOREIGN KEY (`type_id`) REFERENCES `location_types` (`type_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `visits` (
    `visit_id` INT AUTO_INCREMENT PRIMARY KEY,
    `location_id` INT,
    `user_id` INT,
    `check_in` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`location_id`) REFERENCES `locations` (`location_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)

) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `visits_archive` (
    `visit_archive_id` INT AUTO_INCREMENT PRIMARY KEY,
    `visit_id` INT,
    `location_id` INT,
    `user_id` INT,
    `check_in` DATETIME NOT NULL,
    `check_out` DATETIME NOT NULL,
    FOREIGN KEY (`visit_id`) REFERENCES `visits` (`visit_id`),
    FOREIGN KEY (`location_id`) REFERENCES `locations` (`location_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `requests` (
    `request_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT,
    `request_date` DATETIME NOT NULL,
    `visit_start_date` DATE NOT NULL,
    `visit_end_date` DATE NOT NULL,
    `approved` BOOLEAN,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB;