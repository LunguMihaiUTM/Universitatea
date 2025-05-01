package com.universitatea.repository;

import com.universitatea.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByFacultyId(Long facultyId);

}
