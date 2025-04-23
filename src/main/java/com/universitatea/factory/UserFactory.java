package com.universitatea.factory;

import com.universitatea.entity.User;

public interface UserFactory {
    User createUser(String email, String encodedPassword);
}
