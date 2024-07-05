package com.shuaibu.mapper;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;

public class GradeMapper {
    
    public static GradeDto mapToDto(GradeModel gradeModel){

        return GradeDto.builder()
        .id(gradeModel.getId())
        .rangeFrom(gradeModel.getRangeFrom())
        .rangeTo(gradeModel.getRangeTo())
        .grade(gradeModel.getGrade())
        .remark(gradeModel.getRemark())
        .build();
    }

    public static GradeModel mapToModel(GradeDto gradeDto){

        return GradeModel.builder()
        .id(gradeDto.getId())
        .rangeFrom(gradeDto.getRangeFrom())
        .rangeTo(gradeDto.getRangeTo())
        .grade(gradeDto.getGrade())
        .remark(gradeDto.getRemark())
        .build();
    }
}
