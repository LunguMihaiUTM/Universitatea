package com.universitatea.adapter;

import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.entity.StudentCourse;

import java.util.List;

public interface GradeImporter {
    List<StudentCourseDTO> importGrades();
}
