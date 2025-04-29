package com.universitatea.decorator;

import java.math.BigDecimal;
import java.util.Map;

public abstract class StudentDecorator implements StudentComponent {

    protected final StudentComponent decoratedStudent;

    protected StudentDecorator(StudentComponent decoratedStudent) {
        this.decoratedStudent = decoratedStudent;
    }

    @Override
    public String getFullName() {
        return decoratedStudent.getFullName();
    }

    @Override
    public Map<String, BigDecimal> getGrades() {
        return decoratedStudent.getGrades();
    }
}

