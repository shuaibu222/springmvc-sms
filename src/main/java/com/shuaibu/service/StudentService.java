package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.StudentDto;

public interface StudentService {
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);
    void saveOrUpdateStudent(StudentDto studentDto);
    void deleteStudent(Long id);
}
