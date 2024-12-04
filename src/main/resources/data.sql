# Location Data
delete from locations;
insert into locations (name, address_line_1, address_line_2, city, postcode)
values ('Cardiff Office', '1 Capital Tower', 'Greyfriars Road', 'Cardiff', 'CF10 3AG');
insert into locations (name, address_line_1, address_line_2, city, postcode)
values ('Penarth Office', 'Penarth Road' , 'Penarth bay', 'Penarth', 'CF10 3AG');
insert into locations (name, address_line_1, address_line_2, city, postcode)
values ('Swansea Office', 'Swansea Road', 'Swansea bay', 'Swansea', 'SA1 1SA');

delete from location_types;
insert into location_types (name)
values ('Other');
insert into location_types (name)
values ('Office');
insert into location_types (name)
values ('Warehouse');
insert into location_types (name)
values ('Construction Site');

delete from requests;
insert into requests (request_date, visit_date) values ('2024-11-25', '2025-01-27');
insert into requests (request_date, visit_date) values ('2024-11-25', '2025-01-27');

delete from users;
insert into users (first_name, last_name, email, company_name)
values ('John', 'Doe', 'john@doe.gmail.com', 'Dor Ltd');
insert into users (first_name, last_name, email, company_name)
values ('Jane', 'Doe', 'jane@doe.com', 'Doe Ltd');
insert into users (first_name, last_name, email, company_name)
values ('John', 'Smith', 'jsmith@gmail.com', 'Smith Ltd');

#Checking whos in the visit tracking data
SELECT f.first_name AS userName, l.name AS locationName, v.check_in AS checkInTime
FROM visits v
         JOIN users f ON v.user_id = f.user_id
         JOIN locations l ON v.location_id = l.location_id
WHERE v.check_out IS NULL;

#Data Inserting in the visit tracking data (Currently on site)
INSERT INTO `visits` (`location_id`, `user_id`, `check_in`)
VALUES
    (1, 1, '2024-12-01 08:30:00'),
    (2, 2, '2024-12-01 09:00:00'),
    (3, 1, '2024-12-02 10:15:00'),
    (1, 3, '2024-12-02 11:00:00'),
    (2, 4, '2024-12-03 14:30:00'),
    (3, 5, '2024-12-03 15:00:00'),
    (1, 2, '2024-12-04 08:45:00'),
    (2, 3, '2024-12-04 09:30:00'),
    (3, 4, '2024-12-04 10:00:00'),
    (1, 5, '2024-12-04 11:45:00');


