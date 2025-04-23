package com.universitatea.controller;

import com.universitatea.dto.GroupDTO;
import com.universitatea.entity.Group;
import com.universitatea.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/clone")
    public ResponseEntity<Group> cloneGroup(@RequestParam Long groupId, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.cloneAndUpdateGroup(groupId, groupDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }
}
