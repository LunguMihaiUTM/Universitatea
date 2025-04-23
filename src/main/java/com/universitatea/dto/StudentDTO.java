package com.universitatea.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentDTO {
    private String firstName;
    private String lastName;
    private String registrationNumber;
    private LocalDate birthDate;
    private Long groupId;
}
