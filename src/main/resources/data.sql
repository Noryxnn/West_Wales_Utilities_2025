-- Clear existing data in the tables (ensure no foreign key violations)
DELETE FROM requests;
DELETE FROM locations;
DELETE FROM users;
DELETE FROM location_types;

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
INSERT INTO users (first_name, last_name, password, email, company_name)
VALUES
    ('John', 'Doe', 'password123', 'john@doe.gmail.com', 'Doe Ltd'),
    ('Jane', 'Doe', 'password456', 'jane@doe.com', 'Doe Ltd'),
    ('John', 'Smith', 'password789', 'jsmith@gmail.com', 'Smith Ltd');


-- Insert data into requests
INSERT INTO requests (user_id, request_date, visit_start_date, visit_end_date, is_approved)
VALUES
    (1, '2020-01-01 09:00:00', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), true),
    (2, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', false),
    (3, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', false);

INSERT INTO roles (role_name) VALUES
                                  ('ROLE_ADMIN'),
                                  ('ROLE_USER'),
                                  ('ROLE_STAFF');

INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1), -- John Doe is an admin
                                              (2, 2), -- Jane Smith is a regular user
                                              (2, 3); -- Jane Smith is also a staff member


INSERT INTO users (first_name, last_name, password, email, company_name)
VALUES ('Jane', 'Doe', '{bcrypt}$2a$10$hashedPassword', 'jane.doe@example.com', 'Doe Ltd');

INSERT INTO roles (role_name) VALUES ('ROLE_STAFF');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT user_id FROM users WHERE email = 'jane.doe@example.com'),
        (SELECT role_id FROM roles WHERE role_name = 'ROLE_STAFF'));

SELECT * FROM users WHERE email = 'jane.doe@example.com';

SELECT * FROM roles WHERE role_name = 'ROLE_STAFF';

DELETE FROM users
WHERE email = 'jane.doe@example.com'
  AND user_id NOT IN (SELECT MIN(user_id) FROM users WHERE email = 'jane.doe@example.com');

DELETE FROM roles
WHERE role_name = 'ROLE_STAFF'
  AND role_id NOT IN (SELECT MIN(role_id) FROM roles WHERE role_name = 'ROLE_STAFF');

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT user_id FROM users WHERE email = 'jane.doe@example.com'),
        (SELECT role_id FROM roles WHERE role_name = 'ROLE_STAFF'));

SELECT * FROM user_roles WHERE user_id = (SELECT user_id FROM users WHERE email = 'jane.doe@example.com');

select * from roles;