package com.universitatea.controller;

import com.universitatea.adapter.GradeImportService;
import com.universitatea.entity.StudentCourse;
import com.universitatea.facade.GradingFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeController {

    private final GradeImportService gradeImportService;
    private final GradingFacade gradingFacade;


    @PostMapping("/import-from-external")
    public List<StudentCourse> importGrades() {
        return gradeImportService.importFromExternal();
    }

    @PostMapping("/assign")
    public String assignGrade(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam BigDecimal grade,
            @RequestParam LocalDate examDate) {
        return gradingFacade.assignGradeToStudent(studentId, courseId, grade, examDate);
    }


}
