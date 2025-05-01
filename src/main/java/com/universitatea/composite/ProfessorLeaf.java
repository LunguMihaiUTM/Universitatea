package com.universitatea.composite;

import com.universitatea.entity.Professor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProfessorLeaf implements UniversityComponent {

    private final Professor professor;

    @Override
    public String getName() {
        return professor.getFirstName() + " " + professor.getLastName();
    }

    @Override
    public List<UniversityComponent> getChildren() {
        return List.of(); // frunză
    }
}

