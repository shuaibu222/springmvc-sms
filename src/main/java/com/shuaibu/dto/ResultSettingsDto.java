package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultSettingsDto {

    private UUID id;    
    private String section;
    private String regNo;
    private String firstCA;
    private String secondCA;
    private String thirdCA;
    private String fourthCA;
    private String exam;
    private String total;
}
