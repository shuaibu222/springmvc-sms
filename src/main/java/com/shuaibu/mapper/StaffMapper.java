package com.shuaibu.mapper;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;

public class StaffMapper {

    public static StaffDto mapToDto(StaffModel staffModel){

        return StaffDto.builder()
                .id(staffModel.getId())
                .firstName(staffModel.getFirstName())
                .lastName(staffModel.getLastName())
                .userId(staffModel.getUserId())
                .userName(staffModel.getUserName())
                .password(staffModel.getPassword())
                .dateOfBirth(staffModel.getDateOfBirth())
                .employedDate(staffModel.getEmployedDate())
                .homeAddress(staffModel.getHomeAddress())
                .state(staffModel.getState())
                .LGA(staffModel.getLGA())
                .religion(staffModel.getReligion())
                .tribe(staffModel.getTribe())
                .gender(staffModel.getGender())
                .profilePicture(staffModel.getProfilePicture())
                .phoneNumber(staffModel.getPhoneNumber())
                .isActive(staffModel.getIsActive())
                .classTeacherOfId(staffModel.getClassTeacherOfId())
                .headTeacherOfId(staffModel.getHeadTeacherOfId())
                .subjectModelIds(staffModel.getSubjectModelIds())
                .classModelIds(staffModel.getClassModelIds())
                .build();
    }

    public static StaffModel mapToModel(StaffDto staffDto){

        return StaffModel.builder()
                .id(staffDto.getId())
                .firstName(staffDto.getFirstName())
                .lastName(staffDto.getLastName())
                .userId(staffDto.getUserId())
                .userName(staffDto.getUserName())
                .password(staffDto.getPassword())
                .dateOfBirth(staffDto.getDateOfBirth())
                .employedDate(staffDto.getEmployedDate())
                .homeAddress(staffDto.getHomeAddress())
                .state(staffDto.getState())
                .LGA(staffDto.getLGA())
                .religion(staffDto.getReligion())
                .tribe(staffDto.getTribe())
                .gender(staffDto.getGender())
                .profilePicture(staffDto.getProfilePicture())
                .phoneNumber(staffDto.getPhoneNumber())
                .isActive(staffDto.getIsActive())
                .classTeacherOfId(staffDto.getClassTeacherOfId())
                .headTeacherOfId(staffDto.getHeadTeacherOfId())
                .subjectModelIds(staffDto.getSubjectModelIds())
                .classModelIds(staffDto.getClassModelIds())
                .build();
    }
}
