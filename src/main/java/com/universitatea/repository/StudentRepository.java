package com.universitatea.repository;

import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findByGroupId(Long groupId);
    Optional<Student> findByUserId(Long userId);
}
