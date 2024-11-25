delete from requests;
delete from locations;
delete from users;

insert into requests (user_id, location_id, request_date, visit_date) values (1, 1, '2024-11-25', '2025-01-25');
insert into requests (user_id, location_id, request_date, visit_date) values (2, 1, '2024-11-25', '2025-01-27');

insert into locations (name) values ('Cardiff Bay');
insert into locations (name) values ('Newport Road');
insert into locations (name) values ('Penarth Road');

insert into users (first_name, last_name) values ('John', 'Doe');
insert into users (first_name, last_name) values ('Jane', 'Doe');
insert into users (first_name, last_name) values ('Alice', 'Smith');