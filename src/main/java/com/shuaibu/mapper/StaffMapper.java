package com.shuaibu.mapper;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

public class StaffMapper {
    
    public static StaffDto mapToDto(StaffModel staffModel){

        return StaffDto.builder()
                .id(staffModel.getId())
                .firstName(staffModel.getFirstName())
                .lastName(staffModel.getLastName())
                .userName(staffModel.getUserName())
                .password(staffModel.getPassword())
                .dateOfBirth(staffModel.getDateOfBirth())
                .startDate(staffModel.getStartDate())
                .homeAddress(staffModel.getHomeAddress())
                .state(staffModel.getState())
                .LGA(staffModel.getLGA())
                .religion(staffModel.getReligion())
                .tribe(staffModel.getTribe())
                .gender(staffModel.getGender())
                .profilePicture(staffModel.getProfilePicture())
                .phoneNumber(staffModel.getPhoneNumber())
                .subjectModelIds(staffModel.getSubjectModelIds())
                .classModelIds(staffModel.getClassModelIds())
                .roleModelIds(staffModel.getRoleModelIds())
                .build();
    }

    public static StaffModel mapToModel(StaffDto staffDto){

        return StaffModel.builder()
                .id(staffDto.getId())
                .firstName(staffDto.getFirstName())
                .lastName(staffDto.getLastName())
                .userName(staffDto.getUserName())
                .password(staffDto.getPassword())
                .dateOfBirth(staffDto.getDateOfBirth())
                .startDate(staffDto.getStartDate())
                .homeAddress(staffDto.getHomeAddress())
                .state(staffDto.getState())
                .LGA(staffDto.getLGA())
                .religion(staffDto.getReligion())
                .tribe(staffDto.getTribe())
                .gender(staffDto.getGender())
                .profilePicture(staffDto.getProfilePicture())
                .phoneNumber(staffDto.getPhoneNumber())
                .subjectModelIds(staffDto.getSubjectModelIds())
                .classModelIds(staffDto.getClassModelIds())
                .roleModelIds(staffDto.getRoleModelIds())
                .build();
    }
}
