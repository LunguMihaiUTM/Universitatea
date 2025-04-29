package com.universitatea.facade;

import com.universitatea.entity.Student;
import com.universitatea.entity.Course;
import com.universitatea.entity.StudentCourse;
import com.universitatea.repository.StudentCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentGradingService {

    private final StudentCourseRepository studentCourseRepository;

    public String assignGrade(Student student, Course course, BigDecimal grade, LocalDate examDate) {
        if (grade.compareTo(BigDecimal.ZERO) < 0 || grade.compareTo(BigDecimal.TEN) > 0) {
            return "Grade must be between 0 and 10.";
        }

        Optional<StudentCourse> existing = studentCourseRepository.findByStudentAndCourse(student, course);

        if (existing.isPresent()) {
            return "Grade already exists for this student and course.";
        }

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setGrade(grade);
        studentCourse.setExamDate(examDate);
        studentCourseRepository.save(studentCourse);
        return "Grade assigned successfully.";
    }
}
