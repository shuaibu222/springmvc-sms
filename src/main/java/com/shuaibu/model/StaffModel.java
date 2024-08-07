package com.shuaibu.model;

import java.util.Set;

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
    private Long userId;
    private String userName;
    private String password;
    private String dateOfBirth;
    private String employedDate;
    private String homeAddress;
    private String state;
    private String LGA;
    private String religion;
    private String tribe;
    private String gender;
    private String profilePicture;
    private String phoneNumber;
    private String isActive;
    private String classTeacherOfId; // he's class teacher of
    private String headTeacherOfId;

    @ElementCollection
    private Set<Long> subjectModelIds;

    @ElementCollection
    private Set<Long> classModelIds;
}
