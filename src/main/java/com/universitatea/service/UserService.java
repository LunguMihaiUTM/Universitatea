package com.universitatea.service;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;

public interface UserService {
    StudentDTO updateStudent(Long userId, StudentDTO studentDTO);
    ProfessorDTO updateProfessor(Long userId, ProfessorDTO professorDTO);
}