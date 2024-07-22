package com.shuaibu.service;

import com.shuaibu.dto.HeadTeacherDto;

import java.util.List;

public interface HeadTeacherService {
    List<HeadTeacherDto> getAllHeadTeachers();
    HeadTeacherDto getHeadTeacherById(Long id);
    void saveOrUpdateHeadTeacher(HeadTeacherDto headTeacherDto);
    void deleteHeadTeacher(Long id);
}
