create table professors (
                            id            bigint       not null primary key,
                            first_name    varchar(255) not null,
                            last_name     varchar(255) not null,
                            department_id bigint       not null,
                            type          varchar(50)  not null,
                            user_id       bigint       unique,
                            constraint fk_professor_department foreign key (department_id) references departments (id),
                            constraint fk_user_professor foreign key (user_id) references users (id)
);

alter table professors owner to root;
create sequence professor_id_seq;
alter sequence professor_id_seq owner to root;
