package com.shuaibu.model;

import java.util.List;

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
public class StaffModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private List<Long> subjectModels;
    private List<Long> schoolClassModels;
}
