delete from requests;
delete from locations;
delete from users;

insert into requests (user_id, location_id, request_date, visit_date) values (1, 1, '2024-11-25', '2025-01-27');
insert into requests (user_id, location_id, request_date, visit_date) values (2, 1, '2024-11-25', '2025-01-27');

insert into locations (location_id, name, address_line_1, address_line_2, city, postcode, type_id) values (1, 'Cardiff Office', '1 Capital Tower', 'Greyfriars Road', 'Cardiff', 'CF10 3AG', 1);
insert into locations (location_id, name, address_line_1, address_line_2, city, postcode, type_id) values (2, 'Penarth Office', '1 Penarth Road', '', 'Penarth', 'CF10 3AG', 1);
insert into locations (location_id, name, address_line_1, address_line_2, city, postcode, type_id) values (3, 'Swansea Office', '1 Swansea Road', '', 'Swansea', 'SA1 1SA', 1);

insert into users (first_name, last_name, email, company_name) values ('John', 'Doe', 'john@doe.gmail.com', 'Dor Ltd');
insert into users (first_name, last_name, email, company_name) values ('Jane', 'Doe', 'jane@doe.com', 'Doe Ltd');
insert into users (first_name, last_name, email, company_name) values ('John', 'Smith', 'jsmith@gmail.com', 'Smith Ltd');
