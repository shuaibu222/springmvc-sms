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
public class SchoolClassModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;
    private String sectionId;
    private String classTeacher;

    @ElementCollection
    private Set<Long> subjectModels;

    @ElementCollection
    private Set<Long> staffModels;
}
