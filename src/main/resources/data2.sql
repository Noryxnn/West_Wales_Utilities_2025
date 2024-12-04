# Location Data

DELETE FROM users;
DELETE FROM roles;
DELETE FROM user_roles;
DELETE FROM locations;
DELETE FROM location_types;
DELETE FROM visits;
DELETE FROM visits_archive;
DELETE FROM requests;
DELETE FROM request_status;


insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Cardiff Office', '1 Capital Tower', 'Greyfriars Road', 'Cardiff', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Penarth Office', '2', 'Penarth Road', 'Penarth', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Swansea Office', '3', 'Swansea Road', 'Swansea', 'SA1 1SA', 2);


insert into location_types (name)
values ('Other');
insert into location_types (name)
values ('Office');
insert into location_types (name)
values ('Warehouse');
insert into location_types (name)
values ('Construction Site');

# insert into requests (user_id, request_date, visit_start_date, visit_end_date) values (1, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01');
# insert into requests (user_id, request_date, visit_start_date, visit_end_date) values (2, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01');
# insert into requests (user_id, request_date, visit_start_date, visit_end_date) values (3, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01');

insert into users (first_name, last_name, username, password, email, company_name) values ('John', 'Doe', 'john_doe' 'john123', 'john@doe.gmail.com', 'Doe Ltd');
insert into users (first_name, last_name, username, password, email, company_name) values ('Jane', 'Doe', 'jane_doe' 'jane123', 'jane@doe.com', 'Doe Ltd');
insert into users (first_name, last_name, username, password, email, company_name) values ('John', 'Smith', 'john_smith', 'smith123', 'jsmith@gmail.com', 'Smith Ltd');
