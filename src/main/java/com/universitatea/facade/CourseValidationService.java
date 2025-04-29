package com.universitatea.facade;

import com.universitatea.entity.Course;
import com.universitatea.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CourseValidationService {

    private final CourseRepository courseRepository;

    public Course validateCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}
