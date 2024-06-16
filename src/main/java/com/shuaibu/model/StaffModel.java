package com.shuaibu.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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
    private List<UUID> subjectModels;
    private List<UUID> schoolClassModels;
}
