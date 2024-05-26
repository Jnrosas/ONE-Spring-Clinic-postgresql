create table physicians(
id serial primary key,
name varchar(100) not null,
email varchar(100) not null unique,
dni varchar(10) not null unique,
phone_number varchar(11) not null,
specialty varchar(100) not null,
street varchar(100) not null,
city varchar(100) not null,
po_box varchar(10) not null
);
