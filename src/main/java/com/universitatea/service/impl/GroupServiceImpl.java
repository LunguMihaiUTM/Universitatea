package com.universitatea.service.impl;

import com.universitatea.ResourceNotFoundException;
import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Faculty;
import com.universitatea.entity.Group;
import com.universitatea.repository.FacultyRepository;
import com.universitatea.repository.GroupRepository;
import com.universitatea.repository.UserRepository;
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

        Faculty faculty = facultyRepository.findById(updatedData.getFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty with ID " + updatedData.getFaculty().getId() + " not found"));

        if (updatedData.getGroupCode() != null) cloned.setGroupCode(updatedData.getGroupCode());
        if (updatedData.getYear() != null) cloned.setYear(updatedData.getYear());
        if (updatedData.getSpecialization() != null) cloned.setSpecialization(updatedData.getSpecialization());
        if (updatedData.getFaculty() != null && faculty != null) cloned.setFaculty(updatedData.getFaculty());

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
