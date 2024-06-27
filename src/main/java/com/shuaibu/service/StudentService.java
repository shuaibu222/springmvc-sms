package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public interface StudentService {
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);
    StudentModel saveOrUpdateStudent(StudentDto studentDto);
    void deleteStudent(Long id);
}
