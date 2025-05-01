package com.universitatea.composite;

import com.universitatea.entity.Student;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentLeaf implements UniversityComponent {

    private final Student student;

    @Override
    public String getName() {
        return student.getFirstName() + " " + student.getLastName();
    }

    @Override
    public List<UniversityComponent> getChildren() {
        return List.of(); // frunză, deci fără copii
    }
}
