package com.universitatea.decorator;

import com.universitatea.entity.Student;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudentDecoratorService {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository courseRepository;

    public Map<String, BigDecimal> getAllGrades(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentComponent base = new BasicStudent(student, courseRepository);
        return base.getGrades();
    }

    public Map<String, BigDecimal> getFilteredGrades(Long studentId, BigDecimal threshold) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentComponent base = new BasicStudent(student, courseRepository);
        StudentComponent decorated = new FilteredStudent(base, threshold);

        return decorated.getGrades();
    }

    public String getStudentName(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getFirstName() + " " + student.getLastName();
    }
}
