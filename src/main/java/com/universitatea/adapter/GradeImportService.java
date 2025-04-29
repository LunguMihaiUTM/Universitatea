package com.universitatea.adapter;

import com.universitatea.ResourceNotFoundException;
import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.entity.StudentCourse;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeImportService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    public List<StudentCourseDTO> importFromExternal() {
        ExternalGradeAdapter adapter = new ExternalGradeAdapter(
                new ExternalGradeAPI(),
                studentRepository,
                courseRepository,
                studentCourseRepository
        );
        List<StudentCourseDTO> importedDTOs = adapter.importGrades();

        List<StudentCourse> studentCourses = importedDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        studentCourseRepository.saveAll(studentCourses);
        return importedDTOs;
    }

    private StudentCourse convertToEntity(StudentCourseDTO dto) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(dto.getId());
        studentCourse.setGrade(dto.getGrade());
        studentCourse.setExamDate(dto.getExamDate());

        studentCourse.setStudent(studentRepository.findById(dto.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + dto.getStudent().getId())));

        studentCourse.setCourse(courseRepository.findById(dto.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourse().getId())));

        return studentCourse;
    }
}
