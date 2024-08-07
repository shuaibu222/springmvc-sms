package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ReportSheetGradeModel;

@Repository
public interface ReportSheetGradeRepository extends JpaRepository<ReportSheetGradeModel, Long> {}

