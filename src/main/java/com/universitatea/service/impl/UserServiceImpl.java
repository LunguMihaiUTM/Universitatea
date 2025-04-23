package com.universitatea.service.impl;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import com.universitatea.repository.ProfessorRepository;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    private UserServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    public static UserServiceImpl getInstance(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        if (instance == null) {
            instance = new UserServiceImpl(userRepository, studentRepository, professorRepository);
        }
        return instance;
    }

    public Student updateStudent(Long userId, StudentDTO studentDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Student student = studentRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setBirthDate(studentDTO.getBirthDate());

        return studentRepository.save(student);
    }

    public Professor updateProfessor(Long userId, ProfessorDTO professorDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Professor professor = professorRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Professor not found"));

        professor.setFirstName(professorDTO.getFirstName());
        professor.setLastName(professorDTO.getLastName());
        professor.setType(professorDTO.getType());

        return professorRepository.save(professor);
    }
}

