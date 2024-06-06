package com.shuaibu.mapper;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;

public class GradeMapper {
    
    public static GradeDto mapToDto(GradeModel gradeModel){
        GradeDto gradeDto = GradeDto.builder()
        .id(gradeModel.getId())
        .range(gradeModel.getRange())
        .grade(gradeModel.getGrade())
        .remark(gradeModel.getRemark())
        .build();

        return gradeDto;
    }

    public static GradeModel mapToModel(GradeDto gradeDto){
        GradeModel gradeModel =GradeModel.builder()
        .id(gradeDto.getId())
        .range(gradeDto.getRange())
        .grade(gradeDto.getGrade())
        .remark(gradeDto.getRemark())
        .build();

        return gradeModel;
    }
}
