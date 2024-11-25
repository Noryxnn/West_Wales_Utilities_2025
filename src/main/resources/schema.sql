drop table if exists requests;
drop table if exists locations;
drop table if exists users;

create table if not exists requests (
    request_id int auto_increment primary key,
    user_id int,
    location_id int,
    request_date date,
    visit_date date
) engine = InnoDB;

create table if not exists locations (
    location_id int auto_increment primary key,
    name varchar(255) not null
) engine = InnoDB;

create table if not exists users (
    user_id int auto_increment primary key,
    first_name varchar(255),
    last_name varchar(255)
) engine = InnoDB;
