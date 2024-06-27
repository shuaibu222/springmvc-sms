package com.shuaibu.dto;

import com.shuaibu.model.SchoolClassModel;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class SectionDto {
    private Long id;
    
    // Todo: Add validation
    private String sectionName;
    private Set<String> classIds;
}
