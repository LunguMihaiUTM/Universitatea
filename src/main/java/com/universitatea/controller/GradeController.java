package com.universitatea.controller;

import com.universitatea.adapter.GradeImportService;
import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.entity.User;
import com.universitatea.enums.Role;
import com.universitatea.facade.GradingFacade;
import com.universitatea.strategy.AdminGradeDisplayStrategy;
import com.universitatea.strategy.ProfessorGradeDisplayStrategy;
import com.universitatea.strategy.StudentGradeDisplayStrategy;
import com.universitatea.util.TokenExtractServiceImpl;
import com.universitatea.util.UserExtractServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeController {

    private final GradeImportService gradeImportService;
    private final GradingFacade gradingFacade;

    private final TokenExtractServiceImpl tokenExtractService;
    private final UserExtractServiceImpl userExtractService;

    private final StudentGradeDisplayStrategy studentStrategy;
    private final ProfessorGradeDisplayStrategy professorStrategy;
    private final AdminGradeDisplayStrategy adminStrategy;


    @PostMapping("/import-from-external")
    public List<StudentCourseDTO> importGrades() {
        return gradeImportService.importFromExternal();
    }

    @PostMapping("/assign")
    @SecurityRequirement(name = "bearerAuth")
    public String assignGrade(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam BigDecimal grade,
            @RequestParam LocalDate examDate) throws AccessDeniedException {

        String token = tokenExtractService.getToken(authHeader);
        User user = userExtractService.getUser(token);

        if (user.getRole() != Role.PROFESSOR) {
            throw new com.universitatea.exception.UnauthorizedRoleException("Access denied: only professors can assign grades.");
        }

        return gradingFacade.assignGradeToStudent(studentId, courseId, grade, examDate);
    }



    @GetMapping("/get-grades")
    @SecurityRequirement(name = "bearerAuth")
    public List<StudentCourseDTO> getGrades(
            @RequestHeader("Authorization") String authHeader) {

        String token = tokenExtractService.getToken(authHeader);
        User user = userExtractService.getUser(token);

        return switch (user.getRole()) {
            case STUDENT -> studentStrategy.displayGrades(user);
            case PROFESSOR -> professorStrategy.displayGrades(user);
            case ADMIN -> adminStrategy.displayGrades(user);
        };
    }


}
