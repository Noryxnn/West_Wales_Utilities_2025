-- Clear existing data in the tables (ensure no foreign key violations)
DELETE FROM requests;
DELETE FROM locations;
DELETE FROM users;
DELETE FROM location_types;
DELETE FROM roles;
DELETE FROM user_roles;
DELETE FROM visits;

-- Insert data into location_types
INSERT INTO location_types (name)
VALUES
    ('Other'),
    ('Office'),
    ('Warehouse'),
    ('Construction Site');

INSERT INTO locations (name, address_line_1, address_line_2, city, postcode, type_id)
VALUES
    ('Cardiff Office', '1 Greyfriars Road', '', 'Cardiff', 'CF10 3AG', 2),
    ('Penarth Office', '2 Penarth Road', 'Suite 4', 'Penarth', 'CF10 3AG', 2),
    ('Swansea Office', '3 Swansea Road', '', 'Swansea', 'SA1 1SA', 2);


-- Insert data into users (this will use auto-incrementing user_id values)
/*INSERT INTO users (first_name, last_name, password, email, company_name)
VALUES
    ('John', 'Doe', 'john123', 'john@doe.gmail.com', 'Doe Ltd'),
    ('Jane', 'Doe', 'jane123', 'jane@doe.com', 'Doe Ltd'),
    ('John', 'Smith', 'smith123', 'jsmith@gmail.com', 'Smith Ltd');
*/


-- Insert data into requests
INSERT INTO requests (user_id, request_date, visit_start_date, visit_end_date, status)
VALUES
    (1, '2020-01-01 09:00:00', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'APPROVED'),
    (2, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', 'PENDING'),
    (3, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', 'PENDING');

INSERT INTO visits (visit_id, user_id, location_id, check_in_datetime, check_out_datetime)
VALUES
    (1, 1, 1, '2020-01-01 09:00:00', '2020-01-01 17:00:00'),
    (2, 2, 2, '2020-01-01 09:00:00', null),
    (3, 3, 3, '2020-01-01 09:00:00', null);

-- User data
-- All hashed passwords are 'password'
INSERT INTO users (first_name, last_name, password, email, enabled)
VALUES ('Jane', 'Doe', '$2a$12$M7ZrcwHagw8uqaXMk85yf.sZpsaA7KdbKKXjpeYyVrOVL3R9RmOtG', 'jane@doe.com', true);

INSERT INTO users (first_name, last_name, password, email, enabled)
VALUES ('John', 'Doe', '$2a$12$udyY.WPkASmh2yeC/GafMONiBLKHgzE/tUjc4Ly05eqqXXZRwHIjq', 'john@doe.com', true);

INSERT INTO users (first_name, last_name, password, email, enabled)
VALUES ('John', 'Smith', '$2a$10$YqdzACRpaIFDP2U2bjo2yO250EkJHESG49QQqz/G1UXZHX5AoNABm', 'john@smith.com', true);

INSERT INTO roles (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO roles (role_id, role_name) VALUES (2, 'STAFF');
INSERT INTO roles (role_id, role_name)  VALUES (3, 'VISITOR');

-- Jane Doe is an Admin
INSERT INTO user_roles (email, role_id) VALUES ('jane@doe.com', 1);
-- John Doe is a Staff
INSERT INTO user_roles (email, role_id) values ('john@doe.com', 2);
-- John Smith is a Visitor
INSERT INTO user_roles (email, role_id) VALUES ('john@smith.com', 3);

