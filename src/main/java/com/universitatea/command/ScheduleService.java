package com.universitatea.command;

import com.universitatea.dto.request.ScheduleRequest;
import com.universitatea.entity.Course;
import com.universitatea.entity.Group;
import com.universitatea.entity.Schedule;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.CourseRepository;
import com.universitatea.repository.GroupRepository;
import com.universitatea.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;

    public void scheduleCourse(ScheduleRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setGroup(group);
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setLectureType(request.getLectureType());

        scheduleRepository.save(schedule);
    }
}

