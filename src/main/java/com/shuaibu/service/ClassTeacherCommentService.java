package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.ClassTeacherCommentDto;

public interface ClassTeacherCommentService {
    List<ClassTeacherCommentDto> getAllClassTeacherComments();
    ClassTeacherCommentDto getClassTeacherCommentById(Long id);
    void saveOrUpdateClassTeacherComment(ClassTeacherCommentDto classTeachercommentDto);
    void deleteClassTeacherComment(Long id);
}
