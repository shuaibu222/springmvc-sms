package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class SchoolClassDto {
    private Long id;

    private String className;
    private String sectionId;
    private Set<Long> subjectModels;
    private Set<Long> staffModels;
}
