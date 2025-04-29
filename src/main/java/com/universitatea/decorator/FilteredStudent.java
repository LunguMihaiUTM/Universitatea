package com.universitatea.decorator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class FilteredStudent extends StudentDecorator {

    private final BigDecimal threshold;

    public FilteredStudent(StudentComponent student, BigDecimal threshold) {
        super(student);
        this.threshold = threshold;
    }

    @Override
    public Map<String, BigDecimal> getGrades() {
        return super.getGrades().entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(threshold) >= 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
