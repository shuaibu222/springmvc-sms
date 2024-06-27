package com.shuaibu.mapper;

import com.shuaibu.dto.CommentDto;
import com.shuaibu.model.CommentModel;

public class CommentMapper {
    
    public static CommentDto mapToDto(CommentModel commentModel){
        CommentDto commentDto = CommentDto.builder()
        .id(commentModel.getId())
        .rangeFrom(commentModel.getRangeFrom())
        .rangeTo(commentModel.getRangeTo())
        .remark(commentModel.getRemark())
        .build();

        return commentDto;
    }

    public static CommentModel mapToModel(CommentDto commentDto){
        CommentModel commentModel = CommentModel.builder()
        .id(commentDto.getId())
        .rangeFrom(commentDto.getRangeFrom())
        .rangeTo(commentDto.getRangeTo())  
        .remark(commentDto.getRemark())
        .build();

        return commentModel;
    }
}
