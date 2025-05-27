package com.universitatea.adapter;

import java.time.LocalDate;
import java.util.List;

//Simulation of an External API
public class ExternalGradeAPI {

    public List<ExternalGrade> getGrades() {
        return List.of(
                new ExternalGrade("TI-222", "Dinu Groza", "Tehnologii Web", 10, LocalDate.of(2025, 5, 5)),
                new ExternalGrade("TI-222", "Dinu Groza", "Limba engleză", 10, LocalDate.of(2025, 5, 5))
        );
    }
}
