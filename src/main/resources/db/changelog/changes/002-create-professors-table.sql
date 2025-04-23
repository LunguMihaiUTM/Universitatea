create table professors (
                            id            bigint       not null primary key,
                            first_name    varchar(255) ,
                            last_name     varchar(255) ,
                            department_id bigint       ,
                            type          varchar(50)  ,
                            user_id       bigint       unique,
                            constraint fk_professor_department foreign key (department_id) references departments (id),
                            constraint fk_user_professor foreign key (user_id) references users (id)
);

alter table professors owner to root;
create sequence professor_id_seq;
alter sequence professor_id_seq owner to root;
