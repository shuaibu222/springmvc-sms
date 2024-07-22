package com.shuaibu.service.impl;

import com.shuaibu.mapper.StaffMapper;
import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.UserModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.service.StaffService;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StaffMapper.*;

@Service
public class StaffImpl implements StaffService {

    private final PasswordEncoder passwordEncoder;
    private final SchoolClassRepository schoolClassRepository;
    private final StaffRepository staffRepository;
    private final UserImpl userService;
    private final UserRepository userRepository;

    public StaffImpl(PasswordEncoder passwordEncoder, SchoolClassRepository schoolClassRepository, StaffRepository staffRepository, UserImpl userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.schoolClassRepository = schoolClassRepository;
        this.staffRepository = staffRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<StaffDto> getAllStaffs() {
        List<StaffModel> staffs = staffRepository.findAll();
        return staffs.stream().map(StaffMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public StaffDto getStaffById(Long id) {
        return mapToDto(staffRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateStaff(StaffDto staffDto) {

        if (staffDto.getId() != null) {
            StaffModel existingStaff = staffRepository.findById(staffDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + staffDto.getId()));

            // Delete user associated with the student (if it exists)
            if (existingStaff.getUserId() != null) {
                UserModel userModel = new UserModel();
                userModel.setId(existingStaff.getUserId());
                userModel.setUsername(staffDto.getUserName());
                userModel.setPassword(staffDto.getPassword());
                userModel.setRoles(Collections.singleton("ROLE_STAFF"));

                UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
                staffDto.setUserId(savedUser.getId());
                staffDto.setPassword(passwordEncoder.encode(staffDto.getPassword()));
            }

            // save
            StaffModel staffModel = staffRepository.save(mapToModel(staffDto));

            // TODO
            // clear and add new classes
            for (Long classIds: staffDto.getClassModelIds()) {
                SchoolClassModel schoolClassModel = schoolClassRepository.findById(classIds).orElseThrow();
                schoolClassModel.getStaffModels().add(staffModel.getId());
                schoolClassRepository.save(schoolClassModel);
            }

        } else {
            newUserModel(staffDto);
            StaffModel staffModel = staffRepository.save(mapToModel(staffDto));

            // join classes to a staff
            for (Long classIds: staffDto.getClassModelIds()) {
                SchoolClassModel schoolClassModel = schoolClassRepository.findById(classIds).orElseThrow();
                schoolClassModel.getStaffModels().add(staffModel.getId());
                schoolClassRepository.save(schoolClassModel);
            }
        }
    }
    
    @Override
    public void deleteStaff(Long id) {
        StaffModel staffModel = staffRepository.findById(id).orElseThrow();
        userRepository.deleteById(staffModel.getUserId());

        staffRepository.deleteById(id);
    }

    // UTILITY FUNCTIONS
    private void newUserModel(StaffDto staffDto) {
        UserModel userModel = new UserModel();
        userModel.setUsername(staffDto.getUserName());
        userModel.setPassword(staffDto.getPassword());
        userModel.setRoles(Collections.singleton("ROLE_STAFF"));

        UserModel savedUser = userService.saveUser(UserMapper.mapToDto(userModel));
        staffDto.setUserId(savedUser.getId());
        staffDto.setPassword(passwordEncoder.encode(staffDto.getPassword()));
    }

//    private String getCurrentDateString() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        return LocalDate.now().format(formatter);
//    }
}
