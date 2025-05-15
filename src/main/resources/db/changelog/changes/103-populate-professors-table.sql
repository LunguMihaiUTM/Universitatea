insert into professors (id, first_name, last_name, department_id, type, user_id) values
                                                                                     (nextval('professor_id_seq'), 'Ion', 'Popescu', 1, 'LECTURER', 7),
                                                                                     (nextval('professor_id_seq'), 'Maria', 'Ionescu', 2, 'ASSISTANT', 8),
                                                                                     (nextval('professor_id_seq'), 'Vasile', 'Dumitru', 3, 'PROFESSOR', 9),
                                                                                     (nextval('professor_id_seq'), 'Elena', 'Mihail', 4, 'LECTURER', 10);