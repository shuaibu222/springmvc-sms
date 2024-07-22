package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SectionDto {
    private Long id;

    @NotEmpty(message = "* Section name must be specified")
    private String sectionName;

    private String sectionDesc;
    private String headTeacherId;

    private Set<String> classIds;
}
