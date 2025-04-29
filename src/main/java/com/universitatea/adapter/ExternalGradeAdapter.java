package com.universitatea.adapter;

import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.dto.CourseDTO;
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
    public List<StudentCourseDTO> importGrades() {
        List<ExternalGrade> externalGrades = externalAPI.getGrades();

        return externalGrades.stream()
                .map(grade -> {
                    String[] nameParts = grade.studentName().split(" ");
                    Student student = studentRepository.findByFirstNameAndLastName(nameParts[0], nameParts[1])
                            .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + grade.studentName()));
                    Course course = courseRepository.findByTitle(grade.courseTitle())
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + grade.courseTitle()));

                    Optional<StudentCourse> existing = studentCourseRepository.findByStudentAndCourse(student, course);
                    if (existing.isPresent()) {
                        return null; // already exists, skip
                    }

                    // Crearea obiectului StudentCourseDTO
                    StudentCourseDTO studentCourseDTO = StudentCourseDTO.builder()
                            .student(StudentDTO.builder()
                                    .id(student.getId())
                                    .firstName(student.getFirstName())
                                    .lastName(student.getLastName())
                                    .group(GroupDTO.builder()
                                            .groupCode(grade.groupCode())
                                            .build())
                                    .build())
                            .course(CourseDTO.builder()
                                    .id(course.getId())
                                    .title(course.getTitle())
                                    .credits(course.getCredits())
                                    .build())
                            .grade(BigDecimal.valueOf(grade.grade()))
                            .examDate(grade.examDate())
                            .build();
                    return studentCourseDTO;
                })
                .filter(sc -> sc != null)
                .toList();
    }
}
