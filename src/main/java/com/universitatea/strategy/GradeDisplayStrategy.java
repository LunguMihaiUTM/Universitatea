package com.universitatea.strategy;

import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.entity.User;

import java.util.List;

public interface GradeDisplayStrategy {
    List<StudentCourseDTO> displayGrades(User user);
}
