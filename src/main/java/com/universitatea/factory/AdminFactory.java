package com.universitatea.factory;

import com.universitatea.entity.User;
import com.universitatea.enums.Role;

public class AdminFactory implements UserFactory {
    @Override
    public User createUser(String email, String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .role(Role.ADMIN)
                .build();
    }
}