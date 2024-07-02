create table users(
id serial primary key,
email varchar(100) not null unique,
password varchar(300) not null
);