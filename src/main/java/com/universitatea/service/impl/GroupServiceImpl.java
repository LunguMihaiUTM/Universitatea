package com.universitatea.service.impl;

import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Group;
import com.universitatea.repository.GroupRepository;
import com.universitatea.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group cloneAndUpdateGroup(Long originalGroupId, GroupDTO updatedData) {
        Group original = groupRepository.findById(originalGroupId)
                .orElseThrow(() -> new RuntimeException("Original group not found"));

        Group cloned = original.clone();

        if (updatedData.getGroupCode() != null) cloned.setGroupCode(updatedData.getGroupCode());
        if (updatedData.getYear() != null) cloned.setYear(updatedData.getYear());
        if (updatedData.getSpecialization() != null) cloned.setSpecialization(updatedData.getSpecialization());

        return groupRepository.save(cloned);
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        return groups.stream()
                .map(group ->  GroupDTO.builder()
                        .groupCode(group.getGroupCode())
                        .year(group.getYear())
                        .specialization(group.getSpecialization())
                        .build())
                .toList();
    }

}
