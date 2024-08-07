package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ResultModel;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, Long> {
    List<ResultModel> findResultModelsBySectionIdAndStudentClassId(String section, String classId);

    ResultModel findOneByRegNoAndTermIdAndAcademicSessionId(String regNo, String termId, String academicSessionId);
    
    List<ResultModel> findAllByRegNoAndTermIdAndAcademicSessionId(String regNo, String termId, String academicSessionId);

    List<ResultModel> findResultModelsByStudentClassIdAndTermIdAndAcademicSessionId(String classId, String termId,
            String academicSessionId);
}

