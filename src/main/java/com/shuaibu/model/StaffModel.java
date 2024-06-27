package com.shuaibu.model;

import java.util.List;

import jakarta.persistence.*;
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
    private String userName;
    private String password;
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

    @ElementCollection
    private List<Long> subjectModelIds;

    @ElementCollection
    private List<Long> classModelIds;

    @ElementCollection
    private List<Long> roleModelIds;
}
