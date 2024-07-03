create table appointments(
id serial primary key,
id_patient bigint not null,
id_physician bigint not null,
date timestamp not null,

foreign key (id_patient) references "patients" (id),
foreign key (id_physician) references "physicians" (id)
);