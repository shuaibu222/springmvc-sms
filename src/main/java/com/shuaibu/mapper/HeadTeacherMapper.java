package com.shuaibu.mapper;

import com.shuaibu.dto.HeadTeacherDto;
import com.shuaibu.model.HeadTeacherModel;

public class HeadTeacherMapper {
    
    public static HeadTeacherDto mapToDto(HeadTeacherModel headTeacherModel){

        return HeadTeacherDto.builder()
                .id(headTeacherModel.getId())
                .teacherName(headTeacherModel.getTeacherName())
                .sectionName(headTeacherModel.getSectionName())
                .teacherId(headTeacherModel.getTeacherId())
                .sectionId(headTeacherModel.getSectionId())
        .build();
    }

    public static HeadTeacherModel mapToModel(HeadTeacherDto headTeacherDto){

        return HeadTeacherModel.builder()
                .id(headTeacherDto.getId())
                .teacherName(headTeacherDto.getTeacherName())
                .sectionName(headTeacherDto.getSectionName())
                .teacherId(headTeacherDto.getTeacherId())
                .sectionId(headTeacherDto.getSectionId())
        .build();
    }
}
