package com.universitatea.controller;

import com.universitatea.decorator.StudentDecoratorService;
import com.universitatea.dto.GroupDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.Group;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.impl.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentDecoratorService studentDecoratorService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentServiceImpl studentService;

    @GetMapping("/{id}/grades")
    public Map<String, BigDecimal> getAllGrades(@PathVariable Long id) {
        return studentDecoratorService.getAllGrades(id);
    }

    @GetMapping("/{id}/grades/filtered")
    public Map<String, BigDecimal> getFilteredGrades(
            @PathVariable Long id,
            @RequestParam(defaultValue = "6.00") BigDecimal threshold
    ) {
        return studentDecoratorService.getFilteredGrades(id, threshold);
    }

    @GetMapping("/{id}/name")
    public String getStudentFullName(@PathVariable Long id) {
        Student student = studentRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return student.getFirstName() + " " + student.getLastName();
    }

    @GetMapping("/by-user-id/{userId}")
    public StudentDTO getStudentByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        GroupDTO groupDTO = null;
        if (student.getGroup() != null) {
            Group group = student.getGroup();
            groupDTO = new GroupDTO(
                    group.getGroupCode(),
                    group.getYear(),
                    group.getSpecialization(),
                    group.getFaculty()
            );
        }

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .birthDate(student.getBirthDate())
                .group(groupDTO)
                .email(student.getUser().getEmail())
                .build();
    }

    @GetMapping("by-professor")
    public List<StudentDTO> getStudentsByProfessorId(@RequestParam Long professorId) {
        return studentService.getStudentsByProfessorId(professorId);
    }

    @GetMapping("/enroll")
    public String enrollStudentToCourse(Long studentId, Long courseId){
        return studentService.enrollStudentToCourse(studentId, courseId);
    }


}