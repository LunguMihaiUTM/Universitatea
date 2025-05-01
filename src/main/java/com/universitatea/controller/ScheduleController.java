package com.universitatea.controller;

import com.universitatea.command.Command;
import com.universitatea.command.CommandInvoker;
import com.universitatea.command.ScheduleCourseCommand;
import com.universitatea.command.ScheduleService;
import com.universitatea.dto.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CommandInvoker commandInvoker;

    @PostMapping
    public ResponseEntity<String> scheduleCourse(@RequestBody ScheduleRequest request) {
        Command command = new ScheduleCourseCommand(scheduleService, request);
        commandInvoker.executeCommand(command);
        return ResponseEntity.ok("Course scheduled successfully");
    }
}
