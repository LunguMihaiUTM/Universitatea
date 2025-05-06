package com.universitatea.service.impl;

import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Faculty;
import com.universitatea.entity.Group;
import com.universitatea.repository.FacultyRepository;
import com.universitatea.repository.GroupRepository;
import com.universitatea.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public Group cloneAndUpdateGroup(Long originalGroupId, GroupDTO updatedData) {
        Group original = groupRepository.findById(originalGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Original group not found"));

        Group cloned = original.clone();

        Faculty faculty = updatedData.getFaculty() != null ?
                facultyRepository.findById(updatedData.getFaculty().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Faculty with ID " + updatedData.getFaculty().getId() + " not found"))
                : original.getFaculty();

        cloned.setGroupCode(updatedData.getGroupCode() != null ? updatedData.getGroupCode() : original.getGroupCode());
        cloned.setYear(updatedData.getYear() != null ? updatedData.getYear() : original.getYear());
        cloned.setSpecialization(updatedData.getSpecialization() != null ? updatedData.getSpecialization() : original.getSpecialization());
        cloned.setFaculty(faculty);

        return groupRepository.save(cloned);
    }

    @Override
    public Group cloneGroup(Long originalGroupId) {
        Group original = groupRepository.findById(originalGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Original group not found"));

        Group cloned = original.clone();
        return groupRepository.save(cloned);
    }

    @Override
    public Group getGroup(Long groupId){
        return groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        return groups.stream()
                .map(group ->  GroupDTO.builder()
                        .groupCode(group.getGroupCode())
                        .year(group.getYear())
                        .specialization(group.getSpecialization())
                        .faculty(group.getFaculty())
                        .build())
                .toList();
    }

}
