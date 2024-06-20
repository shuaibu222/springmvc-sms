package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeDto {
    private Long id;
    
    // Todo: Add validation
    private Integer rangeFrom;
    private Integer rangeTo;
    private String grade;
    private String remark;

}
