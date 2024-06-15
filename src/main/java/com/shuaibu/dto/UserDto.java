package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private UUID id;
    
    // Todo: Add validation
    private String username;
    private String password;

}
