package com.universitatea.dto;

import com.universitatea.enums.ProfessorType;
import lombok.Data;

@Data
public class ProfessorDTO {
    private String firstName;
    private String lastName;
    private Long departmentId;
    private ProfessorType type;
}

