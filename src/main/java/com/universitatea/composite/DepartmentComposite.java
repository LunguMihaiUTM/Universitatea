package com.universitatea.composite;
import com.universitatea.entity.Department;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DepartmentComposite implements UniversityComponent {

    private final Department department;
    private final List<UniversityComponent> children = new ArrayList<>();

    @Override
    public String getName() {
        return department.getName();
    }

    @Override
    public List<UniversityComponent> getChildren() {
        return children;
    }

    public void addChild(UniversityComponent component) {
        children.add(component);
    }
}
