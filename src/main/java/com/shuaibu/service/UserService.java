package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.UserDto;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    void saveUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(Long id);
}
