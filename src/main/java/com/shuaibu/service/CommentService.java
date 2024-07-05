package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.CommentDto;

public interface CommentService {
    List<CommentDto> getAllComments();
    CommentDto getCommentById(Long id);
    void saveOrUpdateComment(CommentDto commentDto);
    void deleteComment(Long id);
}
