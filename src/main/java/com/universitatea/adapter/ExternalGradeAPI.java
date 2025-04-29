package com.universitatea.adapter;

import java.time.LocalDate;
import java.util.List;

//Simulation of an External API
public class ExternalGradeAPI {

    public List<ExternalGrade> getGrades() {
        return List.of(
                new ExternalGrade("EA-211", "Elena Cristea", "Prelucrarea Semnalelor", 8.5, LocalDate.of(2024, 6, 10)),
                new ExternalGrade("EA-211", "Elena Cristea", "Testarea Produselor Program", 9.2, LocalDate.of(2024, 6, 10))
        );
    }
}
