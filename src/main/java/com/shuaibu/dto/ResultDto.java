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
    private String firstCA;
    private String secondCA;
    private String termId;
    private String exam;
    private String subjectId;
}
