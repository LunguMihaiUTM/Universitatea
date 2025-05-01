package com.universitatea.repository;

import com.universitatea.entity.Professor;
import com.universitatea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUser(User user);
    List<Professor> findByDepartmentId(Long departmentId);
}
