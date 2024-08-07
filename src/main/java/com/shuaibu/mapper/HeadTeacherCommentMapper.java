package com.shuaibu.mapper;

import com.shuaibu.dto.HeadTeacherCommentDto;
import com.shuaibu.model.HeadTeacherCommentModel;

public class HeadTeacherCommentMapper {
    
    public static HeadTeacherCommentDto mapToDto(HeadTeacherCommentModel headTeacherCommentModel){

        return HeadTeacherCommentDto.builder()
                .id(headTeacherCommentModel.getId())
                .teacherName(headTeacherCommentModel.getTeacherName())
                .sectionName(headTeacherCommentModel.getSectionName())
                .teacherId(headTeacherCommentModel.getTeacherId())
                .sectionId(headTeacherCommentModel.getSectionId())
                .rangeFrom(headTeacherCommentModel.getRangeFrom())
                .rangeTo(headTeacherCommentModel.getRangeTo())
                .remark(headTeacherCommentModel.getRemark())
        .build();
    }

    public static HeadTeacherCommentModel mapToModel(HeadTeacherCommentDto headTeacherCommentDto){

        return HeadTeacherCommentModel.builder()
                .id(headTeacherCommentDto.getId())
                .teacherName(headTeacherCommentDto.getTeacherName())
                .sectionName(headTeacherCommentDto.getSectionName())
                .teacherId(headTeacherCommentDto.getTeacherId())
                .sectionId(headTeacherCommentDto.getSectionId())
                .rangeFrom(headTeacherCommentDto.getRangeFrom())
                .rangeTo(headTeacherCommentDto.getRangeTo())
                .remark(headTeacherCommentDto.getRemark())
        .build();
    }
}
