package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TermDto {
    private Long id;

    @NotEmpty(message = "* Term name is mandatory")
    private String termName;

    private String isActive;

}
