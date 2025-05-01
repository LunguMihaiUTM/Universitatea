package com.universitatea.composite;

import com.universitatea.entity.Faculty;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FacultyComposite implements UniversityComponent {

    private final Faculty faculty;
    private final List<UniversityComponent> children = new ArrayList<>();

    @Override
    public String getName() {
        return faculty.getName();
    }

    @Override
    public List<UniversityComponent> getChildren() {
        return children;
    }

    public void addChild(UniversityComponent component) {
        children.add(component);
    }
}
