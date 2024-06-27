package com.shuaibu.service.impl;

import com.shuaibu.dto.UserRole;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapToDto(userRepository.findById(id).get());
    }

    @Override
    public UserModel saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(mapToModel(userDto));
    }

    @Override
    public void updateUser(UserDto userDto) {
        userRepository.save(mapToModel(userDto));
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
