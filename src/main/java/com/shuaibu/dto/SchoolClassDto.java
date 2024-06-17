package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolClassDto {
    private Long id;
    
    // Todo: Add validation
    private String sectionName;
    private String className;
}
