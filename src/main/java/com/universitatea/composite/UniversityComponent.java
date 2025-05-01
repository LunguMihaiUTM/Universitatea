package com.universitatea.composite;

import java.util.List;

public interface UniversityComponent {
    String getName();
    List<UniversityComponent> getChildren();
}
