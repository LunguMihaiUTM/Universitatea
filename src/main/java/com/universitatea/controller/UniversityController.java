package com.universitatea.controller;

import com.universitatea.composite.FacultyComposite;
import com.universitatea.composite.UniversityComponent;
import com.universitatea.composite.UniversityComponentDto;
import com.universitatea.composite.UniversityStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityStructureService universityStructureService;

    @GetMapping("/get-faculty-structure/{facultyId}")
    public UniversityComponentDto getStructure(@PathVariable Long facultyId) {
        UniversityComponent structure = universityStructureService.buildUniversityStructure(facultyId);
        return universityStructureService.convertToDto(structure);
    }
}

