package com.universitatea.service.impl;

import com.universitatea.dto.CourseDTO;
import com.universitatea.dto.ProfessorDTO;
import com.universitatea.entity.Course;
import com.universitatea.entity.Professor;
import com.universitatea.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl {

    private final CourseRepository courseRepository;

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().
                map(course -> CourseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .type(course.getType())
                        .credits(course.getCredits())
                        .professor(mapToDTO(course.getProfessor()))
                        .build()
                )
                .toList();
    }

    private ProfessorDTO mapToDTO(Professor professor) {
        return ProfessorDTO.builder()
                .departmentId(professor.getDepartment().getId())
                .type(professor.getType())
                .lastName(professor.getLastName())
                .firstName(professor.getFirstName())
                .build();
    }
}