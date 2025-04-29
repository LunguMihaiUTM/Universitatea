package com.universitatea.repository;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findAllByStudent(Student student);
    Optional<StudentCourse> findByStudentAndCourse(Student student, Course course);
}
