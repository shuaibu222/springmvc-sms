package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.CommentDto;
import com.shuaibu.model.CommentModel;

public interface CommentService {
    List<CommentDto> getAllComments();
    CommentDto getCommentById(Long id);
    CommentModel saveOrUpdateComment(CommentDto commentDto);
    void deleteComment(Long id);
}
