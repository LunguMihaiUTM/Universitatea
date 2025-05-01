package com.universitatea.command;

import com.universitatea.dto.request.ScheduleRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScheduleCourseCommand implements Command {

    private final ScheduleService scheduleService;
    private final ScheduleRequest request;

    @Override
    public void execute() {
        scheduleService.scheduleCourse(request);
    }
}
