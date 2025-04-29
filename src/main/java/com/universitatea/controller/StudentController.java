package com.universitatea.controller;

import com.universitatea.decorator.StudentDecoratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentDecoratorService studentDecoratorService;

    @GetMapping("/{id}/grades")
    public Map<String, BigDecimal> getAllGrades(@PathVariable Long id) {
        return studentDecoratorService.getAllGrades(id);
    }

    @GetMapping("/{id}/grades/filtered")
    public Map<String, BigDecimal> getFilteredGrades(
            @PathVariable Long id,
            @RequestParam(defaultValue = "6.00") BigDecimal threshold
    ) {
        return studentDecoratorService.getFilteredGrades(id, threshold);
    }

    @GetMapping("/{id}/name")
    public String getStudentFullName(@PathVariable Long id) {
        return studentDecoratorService.getStudentName(id);
    }
}

