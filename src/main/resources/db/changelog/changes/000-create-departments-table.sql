create table departments (
                             id   bigint       not null primary key,
                             name varchar(255) not null
);

alter table departments owner to root;
create sequence department_id_seq;
alter sequence department_id_seq owner to root;
