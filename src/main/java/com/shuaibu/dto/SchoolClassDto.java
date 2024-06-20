package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SchoolClassDto {
    private Long id;

    private String sectionId;
    private String className;
    private List<Long> subjectModels;
    private List<Long> staffModels;
}
