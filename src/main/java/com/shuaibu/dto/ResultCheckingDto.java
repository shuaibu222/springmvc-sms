package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultCheckingDto {

    @NotEmpty(message = "* Reg no. is mandatory")
    private String regNo;

}
