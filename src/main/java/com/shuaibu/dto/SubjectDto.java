package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private Long id;
    
    @NotEmpty(message = "* Subject must have a name")
    private String subjectName;

    private String abbr;

}
