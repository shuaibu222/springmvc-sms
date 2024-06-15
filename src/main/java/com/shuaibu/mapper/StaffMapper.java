package com.shuaibu.mapper;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

public class StaffMapper {
    
    public static StaffDto mapToDto(StaffModel staffModel){
        StaffDto staffDto = StaffDto.builder()
        .id(staffModel.getId())
        .firstName(staffModel.getFirstName())
        .lastName(staffModel.getLastName())
        .dateOfBirth(staffModel.getDateOfBirth())
        .homeAddress(staffModel.getHomeAddress())
        .state(staffModel.getState())
        .LGA(staffModel.getLGA())
        .religion(staffModel.getReligion())
        .role(staffModel.getRole())
        .tribe(staffModel.getTribe())
        .gender(staffModel.getGender())
        .profilePicture(staffModel.getProfilePicture())
        .phoneNumber(staffModel.getPhoneNumber())
        .build();

        return staffDto;
    }

    public static StaffModel mapToModel(StaffDto staffDto){
        StaffModel staffModel = StaffModel.builder()
        .id(staffDto.getId())
        .firstName(staffDto.getFirstName())
        .lastName(staffDto.getLastName())
        .dateOfBirth(staffDto.getDateOfBirth())
        .homeAddress(staffDto.getHomeAddress())
        .state(staffDto.getState())
        .LGA(staffDto.getLGA())
        .religion(staffDto.getReligion())
        .role(staffDto.getRole())
        .tribe(staffDto.getTribe())
        .gender(staffDto.getGender())
        .profilePicture(staffDto.getProfilePicture())
        .phoneNumber(staffDto.getPhoneNumber())
        .build();

        return staffModel;
    }
}
