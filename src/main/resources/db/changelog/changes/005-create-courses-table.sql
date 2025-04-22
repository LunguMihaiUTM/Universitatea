create table courses (
                         id          bigint       not null primary key,
                         title       varchar(255) not null,
                         credits     integer      not null,
                         type        varchar(50)  not null,
                         professor_id bigint      not null,
                         constraint fk_course_professor foreign key (professor_id) references professors (id)
);

alter table courses owner to root;
create sequence course_id_seq;
alter sequence course_id_seq owner to root;
