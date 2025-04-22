insert into users (id, email, password, role) values
                                                  (nextval('user_id_seq'), 'student1@university.com', 'password1', 'STUDENT'),
                                                  (nextval('user_id_seq'), 'student2@university.com', 'password2', 'STUDENT'),
                                                  (nextval('user_id_seq'), 'professor1@university.com', 'password3', 'PROFESSOR'),
                                                  (nextval('user_id_seq'), 'professor2@university.com', 'password4', 'PROFESSOR'),
                                                  (nextval('user_id_seq'), 'assistant@university.com', 'password5', 'ASSISTANT');