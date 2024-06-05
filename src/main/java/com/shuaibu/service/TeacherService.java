package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.TeacherDto;
import com.shuaibu.model.TeacherModel;

public interface TeacherService {
    List<TeacherDto> getAllTeachers();
    TeacherDto getTeacherById(Long id);
    TeacherModel saveTeacher(TeacherDto teacherDto);
    void updateTeacher(TeacherDto teacherDto);
    void deleteTeacher(Long id);
}
