create table users (
                       id       bigint       not null primary key,
                       email    varchar(255) not null unique,
                       password varchar(255) not null,
                       role     varchar(50)  not null
);

alter table users owner to root;
create sequence user_id_seq;
alter sequence user_id_seq owner to root;
