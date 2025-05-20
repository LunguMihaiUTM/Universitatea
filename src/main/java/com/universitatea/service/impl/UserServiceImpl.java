package com.universitatea.service.impl;

import com.universitatea.dto.GroupDTO;
import com.universitatea.dto.UserDTO;
import com.universitatea.entity.*;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.dto.ProfessorDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.repository.*;
import com.universitatea.service.UserService;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final GroupRepository groupRepository;
    private final DepartmentRepository departmentRepository;

    private UserServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository, GroupRepository groupRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.groupRepository = groupRepository;
        this.departmentRepository = departmentRepository;
    }

    public static UserServiceImpl getInstance(UserRepository userRepository, StudentRepository studentRepository, ProfessorRepository professorRepository, GroupRepository groupRepository, DepartmentRepository departmentRepository) {
        if (instance == null) {
            instance = new UserServiceImpl(userRepository, studentRepository, professorRepository, groupRepository, departmentRepository);
        }
        return instance;
    }

    @Override
    public StudentDTO updateStudentByUserId(Long userId, Student studentInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for userId: " + userId));

        student.setFirstName(studentInput.getFirstName());
        student.setLastName(studentInput.getLastName());
        student.setBirthDate(studentInput.getBirthDate());

        studentRepository.save(student);

        return mapToStudentDTO(student);
    }


    @Override
    public ProfessorDTO updateProfessor(Professor updatedProfessor) {
        Professor existingProfessor = professorRepository.findById(updatedProfessor.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));

        Department department = departmentRepository.findById(updatedProfessor.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        existingProfessor.setFirstName(updatedProfessor.getFirstName());
        existingProfessor.setLastName(updatedProfessor.getLastName());
        existingProfessor.setType(updatedProfessor.getType());
        existingProfessor.setDepartment(department);

        professorRepository.save(existingProfessor);

        return mapToProfessorDTO(existingProfessor);
    }

    public Group mapToGroup(GroupDTO groupDTO) {
        return Group.builder()
                .groupCode(groupDTO.getGroupCode())
                .year(groupDTO.getYear())
                .faculty(groupDTO.getFaculty())
                .specialization(groupDTO.getSpecialization())
                .build();
    }

    private StudentDTO mapToStudentDTO(Student student) {
        GroupDTO groupDTO = null;
        if (student.getGroup() != null) {
            groupDTO = GroupDTO.builder()
                    .groupCode(student.getGroup().getGroupCode())
                    .year(student.getGroup().getYear())
                    .faculty(student.getGroup().getFaculty())
                    .specialization(student.getGroup().getSpecialization())
                    .build();
        }

        return StudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .birthDate(student.getBirthDate())
                .id(student.getId())
                .group(groupDTO)
                .build();
    }

    private ProfessorDTO mapToProfessorDTO(Professor professor) {
        return ProfessorDTO.builder()
                .firstName(professor.getFirstName())
                .lastName(professor.getLastName())
                .departmentId(professor.getDepartment().getId())
                .build();
    }
}
