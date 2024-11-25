CREATE TABLE `Locations` (
                             `location_id` INT AUTO_INCREMENT PRIMARY KEY,
                             `name` VARCHAR(255) NOT NULL,
                             `address_line_1` VARCHAR(255) NOT NULL,
                             `address_line_2` VARCHAR(255),
                             `city` VARCHAR(20) NOT NULL,
                             `postcode` VARCHAR(255) NOT NULL,
                             `type_id` INT,
                             FOREIGN KEY (`type_id`) REFERENCES `Location_types` (`type_id`)
);

CREATE TABLE `Location_types` (
                                  `type_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `name` VARCHAR(255)
);


