package com.shuaibu.mapper;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public class StudentMapper {
    
    public static StudentDto mapToDto(StudentModel studentModel){

        return StudentDto.builder()
                .id(studentModel.getId())
                .firstName(studentModel.getFirstName())
                .lastName(studentModel.getLastName())
                .userId(studentModel.getUserId())
                .userName(studentModel.getUserName())
                .password(studentModel.getPassword())
                .regNo(studentModel.getRegNo())
                .dateOfBirth(studentModel.getDateOfBirth())
                .admissionDate(studentModel.getAdmissionDate())
                .homeAddress(studentModel.getHomeAddress())
                .termId(studentModel.getTermId())
                .sessionId(studentModel.getSessionId())
                .sectionId(studentModel.getSectionId())
                .sectionName(studentModel.getSectionName())
                .state(studentModel.getState())
                .LGA(studentModel.getLGA())
                .religion(studentModel.getReligion())
                .studentClassId(studentModel.getStudentClassId())
                .studentClassName(studentModel.getStudentClassName())
                .sportHouseId(studentModel.getSportHouseId())
                .tribe(studentModel.getTribe())
                .gender(studentModel.getGender())
                .profilePicture(studentModel.getProfilePicture())
                .isActive(studentModel.getIsActive())
                .admissionType(studentModel.getAdmissionType())
                .phoneNumber(studentModel.getPhoneNumber())
        .build();
    }

    public static StudentModel mapToModel(StudentDto studentDto){

        return StudentModel.builder()
                .id(studentDto.getId())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .userId(studentDto.getUserId())
                .userName(studentDto.getUserName())
                .password(studentDto.getPassword())
                .regNo(studentDto.getRegNo())
                .dateOfBirth(studentDto.getDateOfBirth())
                .admissionDate(studentDto.getAdmissionDate())
                .homeAddress(studentDto.getHomeAddress())
                .termId(studentDto.getTermId())
                .sessionId(studentDto.getSessionId())
                .sectionId(studentDto.getSectionId())
                .sectionName(studentDto.getSectionName())
                .state(studentDto.getState())
                .LGA(studentDto.getLGA())
                .religion(studentDto.getReligion())
                .studentClassId(studentDto.getStudentClassId())
                .studentClassName(studentDto.getStudentClassName())
                .sportHouseId(studentDto.getSportHouseId())
                .tribe(studentDto.getTribe())
                .gender(studentDto.getGender())
                .profilePicture(studentDto.getProfilePicture())
                .isActive(studentDto.getIsActive())
                .admissionType(studentDto.getAdmissionType())
                .phoneNumber(studentDto.getPhoneNumber())
        .build();
    }
}
