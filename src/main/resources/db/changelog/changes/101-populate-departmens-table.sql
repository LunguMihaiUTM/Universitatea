insert into departments (id, name, faculty_id) values
                                                   (nextval('department_id_seq'), 'Computer Systems', nextval('faculty_id_seq')),
                                                   (nextval('department_id_seq'), 'Software Engineering', nextval('faculty_id_seq')),
                                                   (nextval('department_id_seq'), 'Mechanical Engineering', nextval('faculty_id_seq')),
                                                   (nextval('department_id_seq'), 'Finance and Accounting', nextval('faculty_id_seq'));
