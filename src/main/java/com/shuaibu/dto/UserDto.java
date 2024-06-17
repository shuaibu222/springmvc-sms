package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    
    // Todo: Add validation
    private String username;
    private String password;

}
