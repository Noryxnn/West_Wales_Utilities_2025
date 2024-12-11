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


-- Insert dummy data into `visits` table
INSERT INTO visits (user_id, location_id, check_in_time, check_out_time) VALUES
                                                                             (1, 1, '2024-12-10 09:00:00', '2024-12-10 17:00:00'),
                                                                             (2, 2, '2024-12-10 08:30:00', '2024-12-10 16:30:00'),
                                                                             (3, 3, '2024-12-11 10:00:00', NULL), -- Check-out time not yet recorded
                                                                             (1, 2, '2024-12-09 13:00:00', '2024-12-09 18:00:00'),
                                                                             (2, 3, '2024-12-08 11:00:00', '2024-12-08 15:00:00');
