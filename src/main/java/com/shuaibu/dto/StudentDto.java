package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private Long id;
    
    // Todo: Add validation
    private String firstName;
    private String lastName;
    private String regNo;
    private String dateOfBirth;
    private String admissionDate;
    private String homeAddress;
    private String term;
    private String state;
    private String LGA;
    private String religion;
    private String section;
    private Long studentClass;
    private String sportHouse;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;
}
