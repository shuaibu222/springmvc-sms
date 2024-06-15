package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public interface StudentService {
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(UUID id);
    StudentModel saveStudent(StudentDto studentDto);
    void updateStudent(StudentDto studentDto);
    void deleteStudent(UUID id);
}
