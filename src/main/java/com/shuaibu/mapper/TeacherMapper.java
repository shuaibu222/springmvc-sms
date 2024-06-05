package com.shuaibu.mapper;

import com.shuaibu.dto.TeacherDto;
import com.shuaibu.model.TeacherModel;

public class TeacherMapper {
    
    public static TeacherDto mapToDto(TeacherModel teacherModel){
        TeacherDto teacherDto = TeacherDto.builder()
        .id(teacherModel.getId())
        .firstName(teacherModel.getFirstName())
        .lastName(teacherModel.getLastName())
        .dateOfBirth(teacherModel.getDateOfBirth())
        .homeAddress(teacherModel.getHomeAddress())
        .state(teacherModel.getState())
        .LGA(teacherModel.getLGA())
        .religion(teacherModel.getReligion())
        .section(teacherModel.getSection())
        .tribe(teacherModel.getTribe())
        .gender(teacherModel.getGender())
        .profilePicture(teacherModel.getProfilePicture())
        .phoneNumber(teacherModel.getPhoneNumber())
        .build();

        return teacherDto;
    }

    public static TeacherModel mapToModel(TeacherDto teacherDto){
        TeacherModel teacherModel = TeacherModel.builder()
        .id(teacherDto.getId())
        .firstName(teacherDto.getFirstName())
        .lastName(teacherDto.getLastName())
        .dateOfBirth(teacherDto.getDateOfBirth())
        .homeAddress(teacherDto.getHomeAddress())
        .state(teacherDto.getState())
        .LGA(teacherDto.getLGA())
        .religion(teacherDto.getReligion())
        .section(teacherDto.getSection())
        .tribe(teacherDto.getTribe())
        .gender(teacherDto.getGender())
        .profilePicture(teacherDto.getProfilePicture())
        .phoneNumber(teacherDto.getPhoneNumber())
        .build();

        return teacherModel;
    }
}
