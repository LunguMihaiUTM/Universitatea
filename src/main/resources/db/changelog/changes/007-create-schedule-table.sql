create table schedule (
                          id            bigint       not null primary key,
                          course_id     bigint       not null,
                          group_id      bigint       not null,
                          day_of_week   varchar(20)  not null,
                          start_time    time         not null,
                          end_time      time         not null,
                          lecture_type  varchar(50)  not null,
                          constraint fk_course_schedule foreign key (course_id) references courses (id),
                          constraint fk_group_schedule foreign key (group_id) references groups (id)
);

alter table schedule owner to root;
create sequence schedule_id_seq;
alter sequence schedule_id_seq owner to root;
