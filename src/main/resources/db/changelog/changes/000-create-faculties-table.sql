create table faculties (
                           id    bigint       not null primary key,
                           name  varchar(255) not null unique
);

alter table faculties owner to root;

create sequence faculty_id_seq;

alter sequence faculty_id_seq owner to root;
