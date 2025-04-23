package com.universitatea.service;

import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Group;

import java.util.List;

public interface GroupService {
    Group cloneAndUpdateGroup(Long originalGroupId, GroupDTO updatedData);
    List<GroupDTO> getAllGroups();
}
