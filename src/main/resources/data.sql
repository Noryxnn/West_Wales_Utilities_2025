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
    ('Construction Site'),
    ('Off-site Location'),
    ('Distribution Center');

INSERT INTO locations (name, address_line_1, address_line_2, city, postcode, type_id)
VALUES
    ('Cardiff Office', '1 Greyfriars Road', '', 'Cardiff', 'CF10 3AG', 2),
    ('Penarth Office', '2 Penarth Road', 'Suite 4', 'Penarth', 'CF10 3AG', 2),
    ('Swansea Office', '3 Swansea Road', '', 'Swansea', 'SA1 1SA', 2),
    ('London Construction Site', '25 Building Lane', '', 'London', 'EC1A 1AA', 4),
    ('Manchester Distribution Center', '50 Trade Street', '', 'Manchester', 'M1 2AB', 7),
    ('Leeds Manufacturing Plant', '100 Factory Avenue', '', 'Leeds', 'LS10 1XY', 6),
    ('Edinburgh Construction Site', '75 Workyard Way', '', 'Edinburgh', 'EH1 2NG', 4);

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
VALUES ('Jane', 'Doe', '$2a$12$xaF6TN.gUNemry3Vxx98bukcKk/bhBP8JnpCov/Ro9Hh/82czfx8.', 'jane@gmail.com', true);

INSERT INTO users (first_name, last_name, password, email, enabled)
VALUES ('John', 'Doe', '$2a$12$udyY.WPkASmh2yeC/GafMONiBLKHgzE/tUjc4Ly05eqqXXZRwHIjq', 'john@gmail.com', true);

INSERT INTO users (first_name, last_name, password, email, enabled)
VALUES ('John', 'Smith', '$2a$12$udyY.WPkASmh2yeC/GafMONiBLKHgzE/tUjc4Ly05eqqXXZRwHIjq', 'smith@gmail.com', true);

INSERT INTO roles (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO roles (role_id, role_name) VALUES (2, 'STAFF');
INSERT INTO roles (role_id, role_name)  VALUES (3, 'VISITOR');

-- Jane Doe is an Admin
INSERT INTO user_roles (email, role_id) VALUES ('jane@gmail.com', 1);
-- John Doe is a Staff
INSERT INTO user_roles (email, role_id) values ('john@gmail.com', 2);
-- John Smith is a Visitor
INSERT INTO user_roles (email, role_id) VALUES ('smith@gmail.com', 3);

SELECT roles.role_name, users.first_name, users.last_name
FROM user_roles
         JOIN roles ON user_roles.role_id = roles.role_id
         JOIN users ON user_roles.email = users.email;

