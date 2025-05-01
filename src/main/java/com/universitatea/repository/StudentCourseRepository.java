package com.universitatea.repository;

import com.universitatea.entity.Course;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findAllByStudent(Student student);
    Optional<StudentCourse> findByStudentAndCourse(Student student, Course course);

    @Query("""
    SELECT sc FROM student_course sc
    WHERE sc.course.professor.id = :professorId
""")
    List<StudentCourse> findByCourseProfessorId(@Param("professorId") Long professorId);
}
