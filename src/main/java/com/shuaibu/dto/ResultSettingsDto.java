package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultSettingsDto {

    private Long id;    
    private String sectionId;
    private String regNo;
    private String firstCA;
    private String secondCA;
    private String thirdCA;
    private String fourthCA;
    private String exam;
    private String total;
}
