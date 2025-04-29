package com.universitatea.dto;

import com.universitatea.enums.ProfessorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    private String firstName;
    private String lastName;
    private Long departmentId;
    private ProfessorType type;
}

