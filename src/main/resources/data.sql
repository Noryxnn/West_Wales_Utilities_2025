# Location Data
delete
from locations;
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Cardiff Office', '1 Capital Tower', 'Greyfriars Road', 'Cardiff', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Penarth Office', '2', 'Penarth Road', 'Penarth', 'CF10 3AG', 2);
insert into locations (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Swansea Office', '3', 'Swansea Road', 'Swansea', 'SA1 1SA', 2);

delete
from location_types;
insert into location_types (name)
values ('Other');
insert into location_types (name)
values ('Office');
insert into location_types (name)
values ('Warehouse');
insert into location_types (name)
values ('Construction Site');

delete
from requests;
insert into requests (user_id, request_date, visit_start_date, visit_end_date, isApproved)
values (1, '2020-01-01 09:00:00', current_date, DATE_ADD(CURRENT_DATE, INTERVAL 1 WEEK), true);
insert into requests (user_id, request_date, visit_start_date, visit_end_date, isApproved)
values (2, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', false);
insert into requests (user_id, request_date, visit_start_date, visit_end_date, isApproved)
values (3, '2020-01-01 09:00:00', '2020-01-01', '2020-01-01', false);

delete from users;
insert into users (first_name, last_name, email, company_name) values ('John', 'Doe', 'john@doe.gmail.com', 'Doe Ltd');
insert into users (first_name, last_name, email, company_name) values ('Jane', 'Doe', 'jane@doe.com', 'Doe Ltd');
insert into users (first_name, last_name, email, company_name) values ('John', 'Smith', 'jsmith@gmail.com', 'Smith Ltd');

DELETE FROM visits;
INSERT INTO visits (location_id, user_id, check_in) VALUES (1, 1, NOW());
INSERT INTO visits (location_id, user_id, check_in) VALUES (2, 2, NOW());
INSERT INTO visits (location_id, user_id, check_in) VALUES (3, 3, NOW());
