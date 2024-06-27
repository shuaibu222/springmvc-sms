package com.shuaibu.dto;

import com.shuaibu.model.SectionModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SchoolClassDto {
    private Long id;

    private String className;
    private String sectionId;
    private List<Long> subjectModels;
    private List<Long> staffModels;
}
