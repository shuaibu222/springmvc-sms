package com.shuaibu.mapper;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public class StudentMapper {
    
    public static StudentDto mapToDto(StudentModel studentModel){
        StudentDto studentDto = StudentDto.builder()
        .id(studentModel.getId())
        .firstName(studentModel.getFirstName())
        .lastName(studentModel.getLastName())
        .regNo(studentModel.getRegNo())
        .dateOfBirth(studentModel.getDateOfBirth())
        .admissionDate(studentModel.getAdmissionDate())
        .homeAddress(studentModel.getHomeAddress())
        .term(studentModel.getTerm())
        .state(studentModel.getState())
        .LGA(studentModel.getLGA())
        .religion(studentModel.getReligion())
        .section(studentModel.getSection())
        .studentClass(studentModel.getStudentClass())
        .sportHouse(studentModel.getSportHouse())
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
        .regNo(studentDto.getRegNo())
        .dateOfBirth(studentDto.getDateOfBirth())
        .admissionDate(studentDto.getAdmissionDate())
        .homeAddress(studentDto.getHomeAddress())
        .term(studentDto.getTerm())
        .state(studentDto.getState())
        .LGA(studentDto.getLGA())
        .religion(studentDto.getReligion())
        .section(studentDto.getSection())
        .studentClass(studentDto.getStudentClass())
        .sportHouse(studentDto.getSportHouse())
        .tribe(studentDto.getTribe())
        .gender(studentDto.getGender())
        .profilePicture(studentDto.getProfilePicture())
        .phoneNumber(studentDto.getPhoneNumber())
        .build();

        return studentModel;
    }
}
