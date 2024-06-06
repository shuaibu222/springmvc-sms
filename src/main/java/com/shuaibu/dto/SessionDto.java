package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionDto {
    private Long id;
    
    // Todo: Add validation
    private String sessionName;
}
