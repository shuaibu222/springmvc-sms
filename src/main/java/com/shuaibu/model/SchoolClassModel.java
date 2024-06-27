package com.shuaibu.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


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

    @ElementCollection
    private List<Long> subjectModels;

    @ElementCollection
    private List<Long> staffModels;
}
