package com.universitatea.adapter;

import com.universitatea.entity.StudentCourse;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeImportService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> importFromExternal() {
        ExternalGradeAdapter adapter = new ExternalGradeAdapter(
                new ExternalGradeAPI(),
                studentRepository,
                courseRepository,
                studentCourseRepository
        );
        List<StudentCourse> imported = adapter.importGrades();
        return studentCourseRepository.saveAll(imported);
    }
}

