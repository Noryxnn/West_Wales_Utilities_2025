CREATE TABLE `Users` (
                         `user_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `first_name` VARCHAR(255) NOT NULL,
                         `last_name` VARCHAR(255),
                         `email` VARCHAR(255) NOT NULL,
                         `company_name` VARCHAR(255) NOT NULL
);


CREATE TABLE `Roles` (
                         `role_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `role_name` VARCHAR(255) NOT NULL
);


CREATE TABLE `User_roles`
(
                        `user_role_id` INT AUTO_INCREMENT PRIMARY KEY,
                        `user_id`      INT,
                        `role_id`      INT,
                         FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                         FOREIGN KEY (`role_id`) REFERENCES `Roles` (`role_id`)
);

CREATE TABLE `Visit` (
                         `visit_id` INT AUTO_INCREMENT PRIMARY KEY,
                         `location_id` INT,
                         `user_id` INT,
                         `check_in` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                         FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `visit_archive` (
                                 `visit_archive_id` INT AUTO_INCREMENT PRIMARY KEY,
                                 `visit_id` INT,
                                 `location_id` INT,
                                 `user_id` INT,
                                 `check_in` DATETIME NOT NULL,
                                 `check_out` DATETIME NOT NULL,
                                 FOREIGN KEY (`visit_id`) REFERENCES `Visit` (`visit_id`),
                                 FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                                 FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
