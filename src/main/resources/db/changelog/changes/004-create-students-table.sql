create table students (
                          id               bigint       not null primary key,
                          first_name       varchar(255) not null,
                          last_name        varchar(255) not null,
                          registration_number varchar(50) not null,
                          birth_date       date         not null,
                          group_id         bigint       not null,
                          user_id          bigint       unique,
                          constraint fk_student_group foreign key (group_id) references groups (id),
                          constraint fk_user_student foreign key (user_id) references users (id)
);

alter table students owner to root;
create sequence student_id_seq;
alter sequence student_id_seq owner to root;
