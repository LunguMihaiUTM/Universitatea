package com.universitatea.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.universitatea.enums.LectureType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleRequest {
    private Long courseId;
    private Long groupId;
    private String dayOfWeek;
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm", example = "12:30")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm", example = "13:30")
    private LocalTime endTime;
    private LectureType lectureType;
}

