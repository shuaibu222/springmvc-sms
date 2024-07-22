package com.shuaibu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultSettingsDto {

    private Long id;

    private String sectionId;

    @NotNull(message = "* Must at least specify first CA in numbers")
    @Min(value = 10, message = "* Range must start from 10")
    private Long firstCA;

    @NotNull(message = "* Must at least specify second CA in numbers")
    @Min(value = 10, message = "* Range must start from 10")
    private Long secondCA;

    private Long thirdCA;
    private Long fourthCA;

    @NotNull(message = "* Must at least specify exam in numbers")
    @Min(value = 10, message = "* Range must start from 40")
    private Long exam;

    private Long total;
}
