drop table if exists location;
create table location (
    id bigint auto_increment primary key,
    name varchar(255),
    address_line_1 varchar(255),
    address_line_2 varchar(255),
    city varchar(255),
    postcode varchar(255),
    type_id bigint
) engine = InnoDB;