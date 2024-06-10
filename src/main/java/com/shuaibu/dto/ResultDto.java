package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {

    private Long id;
    private String name;
    private String section;
    private String academcSession;
    private String regNo;
    private String studentClass;
    private String firstCA;
    private String secondCA;
    private String term;
    private String exam;
    private String subject;
}
