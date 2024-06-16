package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDto {

    private UUID id;
    private String name;
    private String section;
    private String academicSession;
    private String regNo;
    private String studentClass;
    private String firstCA;
    private String secondCA;
    private String term;
    private String exam;
    private String subject;
}
