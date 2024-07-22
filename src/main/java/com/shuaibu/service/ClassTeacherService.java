package com.shuaibu.service;

import com.shuaibu.dto.ClassTeacherDto;

import java.util.List;

public interface ClassTeacherService {
    List<ClassTeacherDto> getAllClassTeachers();
    ClassTeacherDto getClassTeacherById(Long id);
    void saveOrUpdateClassTeacher(ClassTeacherDto classTeacherDto);
    void deleteClassTeacher(Long id);
}
