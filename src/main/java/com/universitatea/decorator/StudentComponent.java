package com.universitatea.decorator;

import java.math.BigDecimal;
import java.util.Map;

public interface StudentComponent {
    String getFullName();
    Map<String, BigDecimal> getGrades();
}
