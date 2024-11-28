# Location Data
delete from locations;
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Cardiff Office', '1 Capital Tower', 'Greyfriars Road', 'Cardiff', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Penarth Office', '2', 'Penarth Road', 'Penarth', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Swansea Office', '3', 'Swansea Road', 'Swansea', 'SA1 1SA', 2);

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
insert into requests (user_id, location_id, request_date, visit_date) values (1, 1, '2024-11-25', '2025-01-27');
insert into requests (user_id, location_id, request_date, visit_date) values (2, 1, '2024-11-25', '2025-01-27');

delete from users;
insert into users (first_name, last_name, email, company_name) values ('John', 'Doe', 'john@doe.gmail.com', 'Dor Ltd');
insert into users (first_name, last_name, email, company_name) values ('Jane', 'Doe', 'jane@doe.com', 'Doe Ltd');
insert into users (first_name, last_name, email, company_name) values ('John', 'Smith', 'jsmith@gmail.com', 'Smith Ltd');