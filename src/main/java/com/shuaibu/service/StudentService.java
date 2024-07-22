package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

public interface StudentService {
    List<StudentDto> getAllStudents();
    List<StudentDto> getStudentsByClassId(Long classId);
    StudentDto getStudentById(Long id);
    void saveOrUpdateStudent(StudentDto studentDto);
    void deleteStudent(Long id);
}
