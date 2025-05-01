package com.universitatea.dto;

import com.universitatea.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private Role role;
}

