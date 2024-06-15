package com.shuaibu.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class SchoolClassModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sectionName;
    private String className;

    @ManyToMany(mappedBy = "schoolClassModels")
    private List<SubjectModel> subjectModels;

    @ManyToMany(mappedBy = "schoolClassModels")
    private List<StaffModel> staffModels;
}
