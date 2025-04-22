package com.universitatea.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "student_course")
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_course_id_seq")
    @SequenceGenerator(name = "student_course_id_seq", sequenceName = "student_course_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "grade")
    private BigDecimal grade;

    @Column(name = "exam_date")
    private LocalDate examDate;
}
