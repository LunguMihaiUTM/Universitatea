package com.universitatea.factory;

import com.universitatea.enums.Role;

public class UserFactoryProvider {

    public static UserFactory getFactory(Role role) {
        if (role == null || role == Role.STUDENT) {
            return new StudentFactory();
        } else if(role == Role.PROFESSOR) {
            return new ProfessorFactory();
        } else if(role == Role.ADMIN) {
            return new AdminFactory();
        }

        return null;
    }
}