package com.shuaibu.dto;

import java.util.List;
import java.util.UUID;

import com.shuaibu.model.SubjectModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {
    private UUID id;
    
    // Todo: Add validation
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String startDate;
    private String homeAddress;
    private String state;
    private String LGA;
    private String religion;
    private String role;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;
    
    private List<SubjectModel> subjectModel;

}
