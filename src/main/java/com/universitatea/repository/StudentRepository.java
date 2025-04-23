package com.universitatea.repository;

import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);

}
