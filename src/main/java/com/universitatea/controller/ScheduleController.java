package com.universitatea.controller;

import com.universitatea.command.Command;
import com.universitatea.command.CommandInvoker;
import com.universitatea.command.ScheduleCourseCommand;
import com.universitatea.command.ScheduleService;
import com.universitatea.dto.request.ScheduleRequest;
import com.universitatea.entity.Group;
import com.universitatea.entity.Schedule;
import com.universitatea.entity.Student;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.ScheduleRepository;
import com.universitatea.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommandInvoker commandInvoker;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;

    @PostMapping
    public ResponseEntity<String> scheduleCourse(@RequestBody ScheduleRequest request) {
        Command command = new ScheduleCourseCommand(scheduleService, request);
        commandInvoker.executeCommand(command);
        return ResponseEntity.ok("Course scheduled successfully");
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<Schedule>> getScheduleByStudent(@PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Group group = student.getGroup();

        List<Schedule> schedule = scheduleRepository.findByGroup(group);
        return ResponseEntity.ok(schedule);
    }

}
