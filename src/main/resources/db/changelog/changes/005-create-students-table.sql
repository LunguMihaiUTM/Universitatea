create table students (
                          id               bigint       not null primary key,
                          first_name       varchar(255) ,
                          last_name        varchar(255) ,
--                           registration_number varchar(50) ,
                          birth_date       date         ,
                          group_id         bigint       ,
                          user_id          bigint       unique,
                          constraint fk_student_group foreign key (group_id) references groups (id),
                          constraint fk_user_student foreign key (user_id) references users (id)
);

alter table students owner to root;
create sequence student_id_seq;
alter sequence student_id_seq owner to root;
