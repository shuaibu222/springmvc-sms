package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserModel saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(Long id);
}
