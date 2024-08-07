package com.shuaibu.mapper;

import com.shuaibu.dto.ReportSheetGradeDto;
import com.shuaibu.model.ReportSheetGradeModel;

public class ReportSheetGradeMapper {
    
    public static ReportSheetGradeDto mapToDto(ReportSheetGradeModel reportSheetGradeModel){

        return ReportSheetGradeDto.builder()
                .id(reportSheetGradeModel.getId())
                .sectionId(reportSheetGradeModel.getSectionId())
                .rangeFrom(reportSheetGradeModel.getRangeFrom())
                .rangeTo(reportSheetGradeModel.getRangeTo())
                .grade(reportSheetGradeModel.getGrade())
                .build();
    }

    public static ReportSheetGradeModel mapToModel(ReportSheetGradeDto reportSheetGradeDto){

        return ReportSheetGradeModel.builder()
                .id(reportSheetGradeDto.getId())
                .sectionId(reportSheetGradeDto.getSectionId())
                .rangeFrom(reportSheetGradeDto.getRangeFrom())
                .rangeTo(reportSheetGradeDto.getRangeTo())
                .grade(reportSheetGradeDto.getGrade())
                .build();
    }
}
