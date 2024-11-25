CREATE TABLE `Requests` (
                            `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                            `user_id` INT,
                            `location_id` INT,
                            `request_date` DATETIME NOT NULL,
                            `visit_date` DATETIME NOT NULL,
                            FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                            FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `request_status` (
                                  `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `user_id` INT,
                                  `location_id` INT,
                                  `request_date` DATETIME NOT NULL,
                                  `visit_date` DATETIME NOT NULL,
                                  `approved` TINYINT NOT NULL,
                                  FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                                  FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
