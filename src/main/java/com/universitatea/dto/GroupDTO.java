package com.universitatea.dto;

import com.universitatea.entity.Faculty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private String groupCode;
    private Integer year;
    private String specialization;
    private Faculty faculty;
}