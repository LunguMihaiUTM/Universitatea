-- insert into students (id, first_name, last_name, registration_number, birth_date, group_id, user_id)
-- values (nextval('student_id_seq'), 'George', 'Ionescu', 'S1234', '2000-05-12', 1, 1),
--        (nextval('student_id_seq'), 'Ana', 'Popa', 'S1235', '1999-03-22', 2, 2),
--        (nextval('student_id_seq'), 'Alex', 'Vasiliu', 'S1236', '2001-07-15', 3, 3),
--        (nextval('student_id_seq'), 'Elena', 'Cristea', 'S1237', '1998-11-02', 4, 4),
--        (nextval('student_id_seq'), 'Alex', 'Cristea', 'S1239', '1998-11-02', 1, 5),
--        (nextval('student_id_seq'), 'Elena', 'Costea', 'S12312', '1998-11-02', 1, 6);


insert into students (id, first_name, last_name, birth_date, group_id, user_id)
values (nextval('student_id_seq'), 'George', 'Ionescu',  '2000-05-12', 1, 1),
       (nextval('student_id_seq'), 'Ana', 'Popa',  '1999-03-22', 2, 2),
       (nextval('student_id_seq'), 'Alex', 'Vasiliu',  '2001-07-15', 3, 3),
       (nextval('student_id_seq'), 'Elena', 'Cristea',  '1998-11-02', 4, 4),
       (nextval('student_id_seq'), 'Alex', 'Cristea',  '1998-11-02', 1, 5),
       (nextval('student_id_seq'), 'Elena', 'Costea',  '1998-11-02', 1, 6);