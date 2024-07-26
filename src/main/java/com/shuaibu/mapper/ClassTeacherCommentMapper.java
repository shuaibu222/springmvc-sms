package com.shuaibu.mapper;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.model.ClassTeacherCommentModel;

public class ClassTeacherCommentMapper {
    
    public static ClassTeacherCommentDto mapToDto(ClassTeacherCommentModel classTeacherCommentModel){

        return ClassTeacherCommentDto.builder()
                .id(classTeacherCommentModel.getId())
                .classId(classTeacherCommentModel.getClassId())
                .className(classTeacherCommentModel.getClassName())
                .teacherId(classTeacherCommentModel.getTeacherId())
                .teacherName(classTeacherCommentModel.getTeacherName())
                .rangeFrom(classTeacherCommentModel.getRangeFrom())
                .rangeTo(classTeacherCommentModel.getRangeTo())
                .remark(classTeacherCommentModel.getRemark())
        .build();
    }

    public static ClassTeacherCommentModel mapToModel(ClassTeacherCommentDto classTeacherCommentDto){

        return ClassTeacherCommentModel.builder()
                .id(classTeacherCommentDto.getId())
                .classId(classTeacherCommentDto.getClassId())
                .className(classTeacherCommentDto.getClassName())
                .teacherId(classTeacherCommentDto.getTeacherId())
                .teacherName(classTeacherCommentDto.getTeacherName())
                .rangeFrom(classTeacherCommentDto.getRangeFrom())
                .rangeTo(classTeacherCommentDto.getRangeTo())
                .remark(classTeacherCommentDto.getRemark())
        .build();
    }
}
