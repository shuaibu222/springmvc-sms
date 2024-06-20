package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {

    private Long id;
    private String name;
    private String sectionId;
    private String academicSessionId;
    private String regNo;
    private String studentClassId;
    private Integer firstCA;
    private Integer secondCA;
    private String termId;
    private Integer exam;
    private String subjectId;
    private Integer total;
    private String grade;
    private String remark;
}
