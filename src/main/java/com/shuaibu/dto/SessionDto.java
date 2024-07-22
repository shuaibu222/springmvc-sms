package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDto {
    private Long id;
    
    @NotEmpty(message = "* Must provide a session")
    private String sessionName;

    private String isActive;
}
