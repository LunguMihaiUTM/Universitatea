package com.universitatea.adapter;

import java.time.LocalDate;
import java.util.List;

//Simulation of an External API
public class ExternalGradeAPI {

    public List<ExternalGrade> getGrades() {
        return List.of(
                new ExternalGrade("TI-221", "George Ionescu", "Advanced Mathematics", 8.5, LocalDate.of(2024, 6, 10)),
                new ExternalGrade("TI-221", "Ana Popa", "Advanced Mathematics", 9.2, LocalDate.of(2024, 6, 10))
        );
    }
}
