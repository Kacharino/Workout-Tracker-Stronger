create table users
(
    id       bigint generated always as identity
        constraint users_pk
            primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null
);

alter table users
    owner to kacharino;

create table excercises
(
    id          bigint generated always as identity
        constraint excercises_pk
            primary key,
    name        varchar(255) not null,
    description varchar(255) not null
);

alter table excercises
    owner to kacharino;

