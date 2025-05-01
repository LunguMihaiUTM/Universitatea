package com.universitatea.service;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;

public interface UserService {
    StudentDTO updateStudent(Long userId, StudentDTO studentDTO);
    ProfessorDTO updateProfessor(Long userId, ProfessorDTO professorDTO);
}