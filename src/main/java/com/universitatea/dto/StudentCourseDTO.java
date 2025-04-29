package com.universitatea.dto;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseDTO {
    private Long id;
    private StudentDTO student;
    private CourseDTO course;
    private BigDecimal grade;
    private LocalDate examDate;
}
