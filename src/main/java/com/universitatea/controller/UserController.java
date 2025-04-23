package com.universitatea.controller;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.repository.ProfessorRepository;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.UserService;
import com.universitatea.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserRepository userRepository,
                          StudentRepository studentRepository,
                          ProfessorRepository professorRepository) {
        this.userService = UserServiceImpl.getInstance(userRepository, studentRepository, professorRepository);
    }

    @PutMapping("/update-student/{userId}")
    public Student updateStudent(@PathVariable Long userId, @RequestBody StudentDTO studentDTO) {
        return userService.updateStudent(userId, studentDTO);
    }

    @PutMapping("/update-professor/{userId}")
    public Professor updateProfessor(@PathVariable Long userId, @RequestBody ProfessorDTO professorDTO) {
        return userService.updateProfessor(userId, professorDTO);
    }
}
