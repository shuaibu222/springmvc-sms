package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ReportSheetModel;

@Repository
public interface ReportSheetRepository extends JpaRepository<ReportSheetModel, Long> {

    ReportSheetModel findByRegNo(String regNo);

    ReportSheetModel findByRegNoAndTermAndAcademicSession(String regNo, String term, String academicSession);
}

