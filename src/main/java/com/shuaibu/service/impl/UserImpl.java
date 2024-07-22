package com.shuaibu.service.impl;

import com.shuaibu.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;
import com.shuaibu.repository.UserRepository;
import com.shuaibu.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.UserMapper.*;

@Service
public class UserImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapToDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserModel saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(mapToModel(userDto));
    }

    @Override
    public void updateUser(UserDto userDto) {
        // Encode password if it's provided
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(mapToModel(userDto));
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
