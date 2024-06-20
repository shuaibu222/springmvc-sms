package com.shuaibu.model;

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
public class ResultModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String sectionId;
    private String academicSessionId;
    private String regNo;
    private String studentClassId;
    private Integer firstCA;
    private Integer secondCA;
    private String termId;
    private Integer exam;
    private String subjectId;
    private Integer total;
    private String grade;
    private String remark;

}
