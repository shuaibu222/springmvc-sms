package com.shuaibu.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {
    private Long id;
    
    // Todo: Add validation
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String dateOfBirth;
    private String startDate;
    private String homeAddress;
    private String state;
    private String LGA;
    private String religion;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;
    private List<Long> subjectModelIds;
    private List<Long> classModelIds;
    private List<Long> roleModelIds;

}
