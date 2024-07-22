package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SchoolClassDto {
    private Long id;

    @NotEmpty(message = "* Class name must be specified. e.g One")
    private String className;

    @NotEmpty(message = "* You must select at least one section")
    private String sectionId;

    private String classTeacher;

    @NotNull(message = "* Must at least select one subject")
    private Set<Long> subjectModels;

    private Set<Long> staffModels;
}
