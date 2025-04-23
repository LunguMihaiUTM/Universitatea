package com.universitatea.service.impl;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.Professor;
import com.universitatea.entity.Student;
import com.universitatea.entity.User;
import com.universitatea.enums.Role;
import com.universitatea.factory.UserFactory;
import com.universitatea.factory.UserFactoryProvider;
import com.universitatea.repository.ProfessorRepository;
import com.universitatea.repository.StudentRepository;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public String signup(UserDTO input) {
        var userOptional = userRepository.findByEmail(input.getEmail());
        if (userOptional.isPresent()) {
            return "Username is already in use";
        }

        //verify the role of the user
        Role role;
        if (input.getRole() == null) {
            role = Role.STUDENT;
        } else {
            role = input.getRole();
        }

        String encodedPassword = passwordEncoder.encode(input.getPassword());

        //save user
        UserFactory factory = UserFactoryProvider.getFactory(role);
        User newUser = factory.createUser(input.getEmail(), encodedPassword);
        userRepository.save(newUser);

        if (role == Role.STUDENT) {
            Student student = new Student();
            student.setUser(newUser);
            studentRepository.save(student);
        } else if (role == Role.PROFESSOR) {
            Professor professor = new Professor();
            professor.setUser(newUser);
            professorRepository.save(professor);
        }
        return "Successfully registered";
    }



//    public void signup(UserDTO input) {
//        var user = userRepository.findByEmail(input.getEmail());
//        if (user.isPresent()) {
//            throw new RuntimeException("Username is already in use");
//        }
//        User newUser = new User();
//        if(input.getRole() == null)
//            newUser.setRole(Role.STUDENT);
//        else newUser.setRole(input.getRole());
//
//        newUser.setPassword(passwordEncoder.encode(input.getPassword()));
//        newUser.setEmail(input.getEmail());
//        userRepository.save(newUser);
//
//    }


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