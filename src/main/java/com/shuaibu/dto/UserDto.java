package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    
    // Todo: Add validation
    private String username;
    private String password;
    private Set<String> roles;

}
