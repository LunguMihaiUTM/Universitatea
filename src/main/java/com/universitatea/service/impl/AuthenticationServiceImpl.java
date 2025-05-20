package com.universitatea.service.impl;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import com.universitatea.entity.Group;
import com.universitatea.enums.Role;
import com.universitatea.factory.UserFactory;
import com.universitatea.factory.UserFactoryProvider;
import com.universitatea.repository.ProfessorRepository;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.UserRepository;
import com.universitatea.repository.GroupRepository;
import com.universitatea.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final GroupRepository groupRepository;

    @Override
    public String signup(UserDTO input) {
        var userOptional = userRepository.findByEmail(input.getEmail());
        if (userOptional.isPresent()) {
            return "Username is already in use";
        }

        Role role = (input.getRole() == null) ? Role.STUDENT : input.getRole();
        String encodedPassword = passwordEncoder.encode(input.getPassword());
        UserFactory factory = UserFactoryProvider.getFactory(role);
        if (factory == null) {
            throw new IllegalArgumentException("Factory for role " + role + " not found");
        }
        User newUser = factory.createUser(input.getEmail(), encodedPassword);

        userRepository.save(newUser);

        if (role == Role.STUDENT) {
            Student student = new Student();
            student.setUser(newUser);
            student.setFirstName("Nume");
            student.setLastName("Prenume");
            student.setBirthDate(LocalDate.of(2000, 1, 1));

            Group defaultGroup = groupRepository.findByGroupCode("Default group")
                    .orElseThrow(() -> new RuntimeException("Grupa implicită nu a fost găsită"));

            student.setGroup(defaultGroup);
            studentRepository.save(student);
        } else if (role == Role.PROFESSOR) {
            Professor professor = new Professor();
            professor.setUser(newUser);
            professorRepository.save(professor);
        }

        return "Successfully registered";
    }

    @Override
    public User authenticate(UserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
