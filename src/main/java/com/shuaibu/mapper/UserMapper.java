package com.shuaibu.mapper;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;

public class UserMapper {
    
    public static UserDto mapToDto(UserModel userModel){
        UserDto userDto = UserDto.builder()
        .id(userModel.getId())
        .username(userModel.getUsername())
        .password(userModel.getPassword())
        .build();

        return userDto;
    }

    public static UserModel mapToModel(UserDto userDto){
        UserModel userModel =UserModel.builder()
        .id(userDto.getId())
        .username(userDto.getUsername())
        .password(userDto.getPassword())
        .build();

        return userModel;
    }
}
