package com.universitatea.repository;

import com.universitatea.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByFacultyId(Long facultyId);
    Optional<Group> findByGroupCode(String groupCode); // 🔧 adăugat pentru grupa implicită
}
