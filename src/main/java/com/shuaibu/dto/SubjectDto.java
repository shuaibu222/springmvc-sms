package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private UUID id;
    
    // Todo: Add validation
    private String subjectName;
    private String abbr;

}
