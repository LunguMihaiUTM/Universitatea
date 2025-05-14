package com.universitatea.controller;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        List<Course> courses = studentCourseRepository.findAllByStudent(student)
                .stream()
                .map(StudentCourse::getCourse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(courses);
    }
}
