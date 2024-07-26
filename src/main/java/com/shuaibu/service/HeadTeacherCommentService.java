package com.shuaibu.service;

import com.shuaibu.dto.HeadTeacherCommentDto;

import java.util.List;

public interface HeadTeacherCommentService {
    List<HeadTeacherCommentDto> getAllHeadTeacherComments();
    HeadTeacherCommentDto getHeadTeacherCommentById(Long id);
    void saveOrUpdateHeadTeacherComment(HeadTeacherCommentDto headTeachercommentDto);
    void deleteHeadTeacherComment(Long id);
}
