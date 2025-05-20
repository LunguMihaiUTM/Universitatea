package com.universitatea.controller;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.repository.*;
import com.universitatea.service.UserService;
import com.universitatea.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserRepository userRepository,
                          StudentRepository studentRepository,
                          ProfessorRepository professorRepository,
                          GroupRepository groupRepository,
                          DepartmentRepository departmentRepository) {
        this.userService = UserServiceImpl.getInstance(userRepository, studentRepository, professorRepository, groupRepository, departmentRepository);
    }

    @PutMapping("/update-student/{userId}")
    public StudentDTO updateStudent(@PathVariable Long userId, @RequestBody Student studentInput) {
        return userService.updateStudentByUserId(userId, studentInput);
    }

    @PutMapping("/update-professor")
    public ProfessorDTO updateProfessor(@RequestBody Professor professor) {
        return userService.updateProfessor(professor);
    }
}
