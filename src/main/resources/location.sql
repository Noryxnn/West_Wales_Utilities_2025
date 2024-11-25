CREATE TABLE `Locations` (
                             `location_id` INT AUTO_INCREMENT PRIMARY KEY,
                             `name` VARCHAR(255) NOT NULL,
                             `address_line_1` VARCHAR(255) NOT NULL,
                             `address_line_2` VARCHAR(255),
                             `city` VARCHAR(20) NOT NULL,
                             `postcode` VARCHAR(255) NOT NULL,
                             `type_id` INT,
                             FOREIGN KEY (`type_id`) REFERENCES `Location_types` (`type_id`),
                             CONSTRAINT UC_Locations UNIQUE (name, address_line_1, city, postcode) /*unique constraints- same name location cant have same address*/
);
ALTER TABLE `Locations` AUTO_INCREMENT=1; /*alters table so auto increment is always set to 1
                                            when inserting stuff for the first time again*/

CREATE TABLE `Location_types` (
                                  `type_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `name` VARCHAR(255),
                                  CONSTRAINT UC_Location_Types UNIQUE (name) -- Ensures no duplicate type names
);
ALTER TABLE `Location_types` AUTO_INCREMENT=1; /*alters table so auto increment is always set to 1
                                            when inserting stuff for the first time again*/


