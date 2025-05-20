package com.universitatea.strategy;

import com.universitatea.dto.CourseDTO;
import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentCourseDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.StudentCourse;
import com.universitatea.entity.User;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentGradeDisplayStrategy implements GradeDisplayStrategy {

    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Override
    public List<StudentCourseDTO> displayGrades(User user) {
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return studentCourseRepository.findAllByStudent(student)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private StudentCourseDTO mapToDTO(StudentCourse sc) {
        StudentDTO studentDTO = new StudentDTO(
                sc.getStudent().getId(),
                sc.getStudent().getFirstName(),
                sc.getStudent().getLastName(),
                null,
                null,
                sc.getStudent().getUser().getEmail()
        );

        Professor professor = sc.getCourse().getProfessor();
        ProfessorDTO professorDTO = new ProfessorDTO(
                professor.getFirstName(),
                professor.getLastName(),
                professor.getDepartment() != null ? professor.getDepartment().getId() : null,
                professor.getType()
        );

        CourseDTO courseDTO = new CourseDTO(
                sc.getCourse().getId(),
                sc.getCourse().getTitle(),
                sc.getCourse().getCredits(),
                sc.getCourse().getType(),
                professorDTO
        );

        return new StudentCourseDTO(
                sc.getId(),
                studentDTO,
                courseDTO,
                sc.getGrade(),
                sc.getExamDate()
        );
    }
}
