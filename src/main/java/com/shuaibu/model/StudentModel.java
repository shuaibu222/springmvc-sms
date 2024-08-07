package com.shuaibu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Long userId;
    private String userName;
    private String password;
    private String regNo;
    private String dateOfBirth;
    private String admissionDate;
    private String homeAddress;
    private String termId;
    private String sessionId;
    private String sectionId;
    private String sectionName;
    private String state;
    private String LGA;
    private String religion;
    private String studentClassId;
    private String studentClassName;
    private String sportHouseId;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;
    private String isActive;
    private String admissionType;
}
