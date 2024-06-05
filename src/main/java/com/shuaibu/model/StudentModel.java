package com.shuaibu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String studentClass;
    private String sportHouse;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;

    
}
