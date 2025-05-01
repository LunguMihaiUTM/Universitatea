package com.universitatea.observer;

import com.universitatea.entity.Course;
import com.universitatea.entity.Student;
import com.universitatea.repository.StudentRepository;
import com.universitatea.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GradeEmailNotificationObserver implements GradeObserver {

    private final EmailService emailService;

    @Override
    public void onGradeAssigned(Student student, Course course, BigDecimal grade) {
        String email = student.getUser().getEmail();
        String message = String.format("Ai primit nota %.2f la cursul %s.", grade, course.getTitle());
        emailService.sendEmail(email, "Notă primită", message);
    }
}

