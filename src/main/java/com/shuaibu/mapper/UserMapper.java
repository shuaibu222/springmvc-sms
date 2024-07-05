package com.shuaibu.mapper;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;

public class UserMapper {
    
    public static UserDto mapToDto(UserModel userModel){

        return UserDto.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .roles(userModel.getRoles())
        .build();
    }

    public static UserModel mapToModel(UserDto userDto){

        return UserModel.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .roles(userDto.getRoles())
        .build();
    }
}
