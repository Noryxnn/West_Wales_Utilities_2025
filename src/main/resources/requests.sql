CREATE TABLE `Requests` (
                            `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                            `user_id` INT,
                            `location_id` INT,
                            `request_date` DATETIME NOT NULL,
                            `visit_date` DATETIME NOT NULL,
                            FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                            FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                            CONSTRAINT UC_Requests UNIQUE (`user_id`, `location_id`, `visit_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*A user shouldn't create multiple requests for the same location and visit date.
Prevents duplicate rows and simplifies query logic for checking request existence.*/

CREATE TABLE `request_status` (
                                  `request_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `user_id` INT,
                                  `location_id` INT,
                                  `request_date` DATETIME NOT NULL,
                                  `visit_date` DATETIME NOT NULL,
                                  `approved` TINYINT NOT NULL,
                                  FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
                                  FOREIGN KEY (`location_id`) REFERENCES `Locations` (`location_id`),
                                  CONSTRAINT UC_Request_Status UNIQUE (`request_id`)
                                    -- Ensures a request can have only one status entry
                                    -- Each request_id in request_status should correspond to exactly one status record.
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
