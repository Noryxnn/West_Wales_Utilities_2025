CREATE TABLE `users` (
                         `user_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `first_name` VARCHAR(255) NOT NULL,
                         `last_name` VARCHAR(255),
                         `email` VARCHAR(255) NOT NULL,
                         `company_name` VARCHAR(255) NOT NULL
)ENGINE=InnoDB;


CREATE TABLE `roles` (
                         `role_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `role_name` VARCHAR(255) NOT NULL
)ENGINE=InnoDB;


CREATE TABLE `user_roles`
(
    `user_role_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id`      INT,
    `role_id`      INT,
    FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES `Roles` (`role_id`)
)ENGINE=InnoDB;


CREATE TABLE `location_types` (
                                  `type_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `name` VARCHAR(255)
)ENGINE=InnoDB;


CREATE TABLE `locations` (
                             `location_id` INT AUTO_INCREMENT PRIMARY KEY,
                             `name` VARCHAR(255) NOT NULL,
                             `address_line_1` VARCHAR(255) NOT NULL,
                             `address_line_2` VARCHAR(255),
                             `city` VARCHAR(20) NOT NULL,
                             `postcode` VARCHAR(255) NOT NULL,
                             `type_id` INT,
                             FOREIGN KEY (`type_id`) REFERENCES `Location_types` (`type_id`)
)ENGINE=InnoDB;


CREATE TABLE `visits` (
                         `visit_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `location_id` INT,
                         `user_id` INT,
                         `check_in` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB;


CREATE TABLE `visits_archive` (
                                 `visit_archive_id` INT AUTO_INCREMENT PRIMARY KEY,
                                 `visit_id` INT,
                                 `location_id` INT,
                                 `user_id` INT,
                                 `check_in` DATETIME NOT NULL,
                                 `check_out` DATETIME NOT NULL,
                                 FOREIGN KEY (`visit_id`) REFERENCES `Visit` (`visit_id`),
                                 FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                                 FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB;


CREATE TABLE `requests` (
                            `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                            `user_id` INT,
                            `location_id` INT,
                            `request_date` DATETIME NOT NULL,
                            `visit_date` DATETIME NOT NULL,
                            FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                            FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`)
) ENGINE=InnoDB;


CREATE TABLE `request_status` (
                                  `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `user_id` INT,
                                  `location_id` INT,
                                  `request_date` DATETIME NOT NULL,
                                  `visit_date` DATETIME NOT NULL,
                                  `approved` TINYINT NOT NULL,
                                  FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                                  FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`)
) ENGINE=InnoDB;