package com.shuaibu.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class StudentDto {

    private Long id;

    @NotEmpty(message = "* First name is mandatory")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotEmpty(message = "* Last name is mandatory")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    private Long userId;

    @NotEmpty(message = "* Username is mandatory")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String userName;

    @NotEmpty(message = "* Password is mandatory")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password; // Consider using a password hashing mechanism for security

    private String regNo;

    @NotNull(message = "* Date fo Birth cannot be empty")
    @Past(message = "* Date of birth cannot be in the future")
    private LocalDate dateOfBirth;

    @NotNull(message = "* Employed Date cannot be empty")
    private LocalDate admissionDate;

    @NotEmpty(message = "* Home address is mandatory")
    private String homeAddress;

    @NotEmpty(message = "* Term is mandatory")
    private String termId;

    @NotEmpty(message = "* Session is mandatory")
    private String sessionId;

    @NotEmpty(message = "* Section is mandatory")
    private String sectionId;
    private String sectionName;

    @NotEmpty(message = "* State is mandatory")
    private String state;

    @NotEmpty(message = "* LGA is mandatory")
    private String LGA;

    @NotEmpty(message = "* Religion is mandatory")
    private String religion;

    @NotEmpty(message = "* Class is mandatory")
    private String studentClassId;
    private String studentClassName;

    @NotEmpty(message = "* Sport House is mandatory")
    private String sportHouseId;

    @NotEmpty(message = "* Tribe is mandatory")
    private String tribe;

    @NotEmpty(message = "* Gender is mandatory")
    private String gender;

    @NotEmpty(message = "* Phone Number is mandatory")
    private String phoneNumber;

    @NotEmpty(message = "* Is student currently active in school")
    private String isActive;

    private Double balance;

    @NotEmpty(message = "* Admission Type is mandatory")
    private String admissionType;

    private String profilePicture;
}
