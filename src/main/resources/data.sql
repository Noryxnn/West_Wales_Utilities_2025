insert into location (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Test Location 1', 'Test Address Line 1', 'Test Address Line 2', 'Test City', 'Test Postcode', 2);
insert into location (name, address_line_1, address_line_2, city, postcode, type_id)
values ('Test Location 2', 'Test Address Line 1', 'Test Address Line 2', 'Test City', 'Test Postcode', 1);

insert into location_type (name)
values ('Other');
insert into location_type (name)
values ('Office');
insert into location_type (name)
values ('Warehouse');
insert into location_type (name)
values ('Construction Site');