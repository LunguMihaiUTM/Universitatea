package com.universitatea.repository;

import com.universitatea.entity.Group;
import com.universitatea.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByGroup(Group group);
}
