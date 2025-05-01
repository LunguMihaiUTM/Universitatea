package com.universitatea.composite;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UniversityComponentDto {
    private String name;
    private List<UniversityComponentDto> children;
}
