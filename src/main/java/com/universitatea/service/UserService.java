package com.universitatea.service;

import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;

public interface UserService {
    StudentDTO updateStudentByUserId(Long userId, Student studentInput);
    ProfessorDTO updateProfessor(Professor professorInput);
}