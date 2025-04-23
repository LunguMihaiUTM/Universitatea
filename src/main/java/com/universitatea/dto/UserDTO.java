package com.universitatea.dto;

import com.universitatea.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String password;
    private String email;
    private Role role;
}

