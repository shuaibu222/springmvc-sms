package com.shuaibu.model;

import java.util.List;
import java.util.Set;

import com.shuaibu.dto.ResultDto;

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
public class ReportSheetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String academicSession;
    private String regNo;
    private String studentClass;
    private String sectionName;
    private String term;
    private String noOfStudents;
    private Integer totalMarks;
    private String finalGrade;
    private String classTeacherComment;
    private String headTeacherComment;
    private String termEnding;
    private String nextTermBegins;
    private String signature;

    // General Observations fields
    private String fluency;
    private String adjustmentToClass;
    private String responsiveness;
    private String senseOfResponsibility;
    private String relationshipsWithOtherPupils;
    private String handWriting;
    private String applicationToWork;

    @ElementCollection
    private Set<Long> results;

}
