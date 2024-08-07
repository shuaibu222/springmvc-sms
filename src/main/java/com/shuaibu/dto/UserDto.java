package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;

    @NotEmpty(message = "* Username is mandatory")
    private String username;

    @NotEmpty(message = "* Password is mandatory")
    private String password;

    private String isActive;

    private Set<String> roles;

}
