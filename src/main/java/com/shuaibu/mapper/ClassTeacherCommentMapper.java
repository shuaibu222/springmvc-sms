package com.shuaibu.mapper;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.model.ClassTeacherCommentModel;

public class ClassTeacherCommentMapper {
    
    public static ClassTeacherCommentDto mapToDto(ClassTeacherCommentModel classTeacherCommentModel){

        return ClassTeacherCommentDto.builder()
        .id(classTeacherCommentModel.getId())
        .rangeFrom(classTeacherCommentModel.getRangeFrom())
        .rangeTo(classTeacherCommentModel.getRangeTo())
        .remark(classTeacherCommentModel.getRemark())
        .build();
    }

    public static ClassTeacherCommentModel mapToModel(ClassTeacherCommentDto classTeacherCommentDto){

        return ClassTeacherCommentModel.builder()
        .id(classTeacherCommentDto.getId())
        .rangeFrom(classTeacherCommentDto.getRangeFrom())
        .rangeTo(classTeacherCommentDto.getRangeTo())
        .remark(classTeacherCommentDto.getRemark())
        .build();
    }
}
