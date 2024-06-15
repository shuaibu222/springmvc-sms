package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(UUID id);
    UserModel saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(UUID id);
}
