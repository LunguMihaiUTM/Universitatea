package com.universitatea.decorator;

import com.universitatea.entity.Student;
import com.universitatea.repository.StudentCourseRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicStudent implements StudentComponent {

    private final Student student;
    private final StudentCourseRepository studentCourseRepository;

    public BasicStudent(Student student, StudentCourseRepository studentCourseRepository) {
        this.student = student;
        this.studentCourseRepository = studentCourseRepository;
    }

    @Override
    public String getFullName() {
        return student.getFirstName() + " " + student.getLastName();
    }

    @Override
    public Map<String, BigDecimal> getGrades() {
        return studentCourseRepository.findAllByStudent(student).stream()
                .collect(Collectors.toMap(
                        sc -> sc.getCourse().getTitle(),
                        sc -> sc.getGrade() != null ? sc.getGrade() : BigDecimal.ZERO
                ));
    }
}


