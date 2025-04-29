package com.universitatea.facade;

import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.entity.Student;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentValidationService {

    private final StudentRepository studentRepository;

    public Student validateStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }
}