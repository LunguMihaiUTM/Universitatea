create table student_course (
                                id          bigint       not null primary key,
                                student_id  bigint       not null,
                                course_id   bigint       not null,
                                grade       decimal(5,2),
                                exam_date   date,
                                constraint fk_student_course_student foreign key (student_id) references students (id),
                                constraint fk_student_course_course foreign key (course_id) references courses (id)
);

alter table student_course owner to root;
create sequence student_course_id_seq;
alter sequence student_course_id_seq owner to root;
