package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportSheetGradeDto {
    private Long id;

    private String sectionId;

    @NotNull(message = "* Must specify range from in numbers")
    @Min(value = 0, message = "* Range from must be a positive number")
    private Long rangeFrom;

    @NotNull(message = "* Must specify range to in numbers")
    @Min(value = 0, message = "* Range to must be a positive number")
    private Long rangeTo;

    @NotEmpty(message = "* Must specify grade in letter. e.g A, B")
    private String grade;
    
}
