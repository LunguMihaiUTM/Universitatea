package com.universitatea.composite;

import com.universitatea.entity.Group;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GroupComposite implements UniversityComponent {

    private final Group group;
    private final List<UniversityComponent> children = new ArrayList<>();

    @Override
    public String getName() {
        return group.getGroupCode();
    }

    @Override
    public List<UniversityComponent> getChildren() {
        return children;
    }

    public void addChild(UniversityComponent component) {
        children.add(component);
    }
}
