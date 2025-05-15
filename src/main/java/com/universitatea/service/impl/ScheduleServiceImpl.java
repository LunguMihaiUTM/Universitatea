package com.universitatea.service.impl;

import com.universitatea.entity.Schedule;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl {

    private final ScheduleRepository scheduleRepository;

    public Schedule getScheduleById(Long id){
        return scheduleRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Schedule not found"));
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

}
