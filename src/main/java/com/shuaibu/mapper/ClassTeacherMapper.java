package com.shuaibu.mapper;

import com.shuaibu.dto.ClassTeacherDto;
import com.shuaibu.model.ClassTeacherModel;

public class ClassTeacherMapper {
    
    public static ClassTeacherDto mapToDto(ClassTeacherModel classTeacherModel){

        return ClassTeacherDto.builder()
                .id(classTeacherModel.getId())
                .teacherName(classTeacherModel.getTeacherName())
                .className(classTeacherModel.getClassName())
                .teacherId(classTeacherModel.getTeacherId())
                .classId(classTeacherModel.getClassId())
        .build();
    }

    public static ClassTeacherModel mapToModel(ClassTeacherDto classTeacherDto){

        return ClassTeacherModel.builder()
                .id(classTeacherDto.getId())
                .teacherName(classTeacherDto.getTeacherName())
                .className(classTeacherDto.getClassName())
                .teacherId(classTeacherDto.getTeacherId())
                .classId(classTeacherDto.getClassId())
        .build();
    }
}
