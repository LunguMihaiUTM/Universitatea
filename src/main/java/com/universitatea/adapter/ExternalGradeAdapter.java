package com.universitatea.adapter;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ExternalGradeAdapter implements GradeImporter {

    private final ExternalGradeAPI externalAPI;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Override
    public List<StudentCourse> importGrades() {
        List<ExternalGrade> externalGrades = externalAPI.getGrades();

        return externalGrades.stream()
                .map(grade -> {
                    String[] nameParts = grade.studentName().split(" ");
                    Student student = studentRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1])
                            .orElseThrow(() -> new RuntimeException("Student not found: " + grade.studentName()));
                    Course course = courseRepository.findByTitle(grade.courseTitle())
                            .orElseThrow(() -> new RuntimeException("Course not found: " + grade.courseTitle()));

                    Optional<StudentCourse> existing = studentCourseRepository.findByStudentAndCourse(student, course);
                    if (existing.isPresent()) {
                        return null; // already exists, skip
                    }

                    StudentCourse sc = new StudentCourse();
                    sc.setStudent(student);
                    sc.setCourse(course);
                    sc.setGrade(BigDecimal.valueOf(grade.grade()));
                    sc.setExamDate(grade.examDate());
                    return sc;
                })
                .filter(sc -> sc != null)
                .toList();
    }
}
