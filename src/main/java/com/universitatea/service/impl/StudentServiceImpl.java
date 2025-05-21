package com.universitatea.service.impl;

import com.universitatea.dto.GroupDTO;
import com.universitatea.dto.StudentDTO;
import com.universitatea.entity.*;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.ProfessorRepository;
import com.universitatea.repository.StudentCourseRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    public List<StudentDTO> getStudentsByProfessorId(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found"));

        List<StudentCourse> studentCourses = studentCourseRepository.findByCourseProfessorId(professorId);

        Set<Student> students = studentCourses.stream()
                .map(StudentCourse::getStudent)
                .collect(Collectors.toSet());

        return students.stream()
                .map(this::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    private StudentDTO mapToStudentDTO(Student student) {
        Group group = student.getGroup();

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupCode(group.getGroupCode());
        groupDTO.setYear(group.getYear());
        groupDTO.setSpecialization(group.getSpecialization());
        groupDTO.setFaculty(group.getFaculty());

        return new StudentDTO() {{
            setId(student.getId());
            setFirstName(student.getFirstName());
            setLastName(student.getLastName());
            setBirthDate(student.getBirthDate());
            setGroup(groupDTO);
            setEmail(student.getUser().getEmail());
        }};
    }

    public String enrollStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourseRepository.save(studentCourse);

        return "You successfully enrolled to course : " + course.getTitle() + ".";

    }

}