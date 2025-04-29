create table departments (
                             id          bigint       not null primary key,
                             name        varchar(255) not null,
                             faculty_id  bigint       not null,
                             constraint fk_department_faculty foreign key (faculty_id) references faculties (id)
);

alter table departments owner to root;
create sequence department_id_seq;
alter sequence department_id_seq owner to root;
