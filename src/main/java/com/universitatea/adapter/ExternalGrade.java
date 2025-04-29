package com.universitatea.adapter;

import java.time.LocalDate;

public record ExternalGrade(
        String groupCode,
        String studentName,
        String courseTitle,
        double grade,
        LocalDate examDate
) {}

