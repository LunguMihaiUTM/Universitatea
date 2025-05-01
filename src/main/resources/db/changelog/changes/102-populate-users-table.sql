insert into users (id, email, password, role) values
                                                  (nextval('user_id_seq'), 'student1@utm.md', 'password1', 'STUDENT'),
                                                  (nextval('user_id_seq'), 'student2@utm.md', 'password2', 'STUDENT'),
                                                  (nextval('user_id_seq'), 'professor1@utm.md', 'password3', 'PROFESSOR'),
                                                  (nextval('user_id_seq'), 'professor2@utm.md', 'password4', 'PROFESSOR'),
                                                  (nextval('user_id_seq'), 'assistant@utm.md', 'password5', 'PROFESSOR'),
                                                  (nextval('user_id_seq'), 'admin@utm.md', 'adminpassword', 'ADMIN');