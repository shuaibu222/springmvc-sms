package com.shuaibu.mapper;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public class StudentMapper {
    
    public static StudentDto mapToDto(StudentModel studentModel){
        StudentDto studentDto = StudentDto.builder()
                .id(studentModel.getId())
                .firstName(studentModel.getFirstName())
                .lastName(studentModel.getLastName())
                .userName(studentModel.getUserName())
                .password(studentModel.getPassword())
                .regNo(studentModel.getRegNo())
                .dateOfBirth(studentModel.getDateOfBirth())
                .admissionDate(studentModel.getAdmissionDate())
                .homeAddress(studentModel.getHomeAddress())
                .termId(studentModel.getTermId())
                .sectionId(studentModel.getSectionId())
                .state(studentModel.getState())
                .LGA(studentModel.getLGA())
                .religion(studentModel.getReligion())
                .studentClassId(studentModel.getStudentClassId())
                .sportHouseId(studentModel.getSportHouseId())
                .tribe(studentModel.getTribe())
                .gender(studentModel.getGender())
                .profilePicture(studentModel.getProfilePicture())
                .phoneNumber(studentModel.getPhoneNumber())
        .build();

        return studentDto;
    }

    public static StudentModel mapToModel(StudentDto studentDto){
        StudentModel studentModel = StudentModel.builder()
                .id(studentDto.getId())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .userName(studentDto.getUserName())
                .password(studentDto.getPassword())
                .regNo(studentDto.getRegNo())
                .dateOfBirth(studentDto.getDateOfBirth())
                .admissionDate(studentDto.getAdmissionDate())
                .homeAddress(studentDto.getHomeAddress())
                .termId(studentDto.getTermId())
                .sectionId(studentDto.getSectionId())
                .state(studentDto.getState())
                .LGA(studentDto.getLGA())
                .religion(studentDto.getReligion())
                .studentClassId(studentDto.getStudentClassId())
                .sportHouseId(studentDto.getSportHouseId())
                .tribe(studentDto.getTribe())
                .gender(studentDto.getGender())
                .profilePicture(studentDto.getProfilePicture())
                .phoneNumber(studentDto.getPhoneNumber())
        .build();

        return studentModel;
    }
}
