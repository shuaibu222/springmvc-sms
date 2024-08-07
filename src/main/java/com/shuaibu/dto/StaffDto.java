package com.shuaibu.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffDto {

    private Long id;

    @NotBlank(message = "* First name is mandatory")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "* Last name is mandatory")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    private Long userId;

    @NotBlank(message = "* Username is mandatory")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String userName;

    @NotBlank(message = "* Password is mandatory")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @NotNull(message = "* Date of Birth cannot be empty")
    private String dateOfBirth;

    @NotNull(message = "* Employed Date cannot be empty")
    private String employedDate;

    @NotBlank(message = "* Home address is mandatory")
    private String homeAddress;

    @NotBlank(message = "* State is mandatory")
    private String state;

    @NotBlank(message = "* LGA is mandatory")
    private String LGA;

    @NotBlank(message = "* Religion is mandatory")
    private String religion;

    @NotBlank(message = "* Tribe is mandatory")
    private String tribe;

    @NotBlank(message = "8 Gender is mandatory")
    private String gender;

    @NotBlank(message = "* Phone Number is mandatory")
    private String phoneNumber;

    private String profilePicture;

    @NotEmpty(message = "* Is teacher currently active staff")
    private String isActive;

    @NotEmpty(message = "At least one subject is required")
    private Set<Long> subjectModelIds;

    private String classTeacherOfId;
    private String headTeacherOfId;

    @NotEmpty(message = "At least one class is required")
    private Set<Long> classModelIds;

    private Set<Long> roleModelIds;
}
