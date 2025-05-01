package com.universitatea.composite;

import com.universitatea.entity.*;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.iterator.StudentIterator;
import com.universitatea.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityStructureService {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public FacultyComposite buildUniversityStructure(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        FacultyComposite facultyComposite = new FacultyComposite(faculty);

        // Departamente
        List<Department> departments = departmentRepository.findByFacultyId(facultyId);
        for (Department department : departments) {
            DepartmentComposite deptComposite = new DepartmentComposite(department);

            List<Professor> professors = professorRepository.findByDepartmentId(department.getId());
            for (Professor professor : professors) {
                deptComposite.addChild(new ProfessorLeaf(professor));
            }

            facultyComposite.addChild(deptComposite);
        }

        // Grupe
        List<Group> groups = groupRepository.findByFacultyId(facultyId);
        for (Group group : groups) {
            GroupComposite groupComposite = new GroupComposite(group);

            List<Student> students = studentRepository.findByGroupId(group.getId());
            StudentIterator studentIterator = new StudentIterator(students);

            // Utilizarea Iterator pentru studenți
            while (studentIterator.hasNext()) {
                Student student = studentIterator.next();
                groupComposite.addChild(new StudentLeaf(student));
            }

            facultyComposite.addChild(groupComposite);
        }

        return facultyComposite;
    }


    public UniversityComponentDto convertToDto(UniversityComponent component) {
        List<UniversityComponentDto> childrenDtos = component.getChildren().stream()
                .map(this::convertToDto)
                .toList();

        return new UniversityComponentDto(component.getName(), childrenDtos);
    }
}
