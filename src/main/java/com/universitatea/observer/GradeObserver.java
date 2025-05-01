package com.universitatea.observer;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;

import java.math.BigDecimal;

public interface GradeObserver {
    void onGradeAssigned(Student student, Course course, BigDecimal grade);
}
