package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.ReportSheetGradeDto;

public interface ReportSheetGradeService {
    List<ReportSheetGradeDto> getAllReportSheetGrades();
    ReportSheetGradeDto getReportSheetGradeById(Long id);
    void saveReportSheetGrade(ReportSheetGradeDto reportSheetGradeDto);
    void updateReportSheetGrade(ReportSheetGradeDto reportSheetGradeDto);
    void deleteReportSheetGrade(Long id);
}
