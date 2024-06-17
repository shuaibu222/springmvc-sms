package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private Long id;
    
    // Todo: Add validation
    private String subjectName;
    private String abbr;

}
