package com.universitatea.facade;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class GradingFacade {

    private final StudentValidationService studentValidationService;
    private final CourseValidationService courseValidationService;
    private final StudentGradingService studentGradingService;

    public String assignGradeToStudent(Long studentId, Long courseId, BigDecimal grade, LocalDate examDate) {
        Student student = studentValidationService.validateStudent(studentId);
        Course course = courseValidationService.validateCourse(courseId);
        return studentGradingService.assignGrade(student, course, grade, examDate);
    }
}
