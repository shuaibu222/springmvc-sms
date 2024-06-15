package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;
import com.shuaibu.repository.StudentRepository;
import com.shuaibu.service.StudentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.StudentMapper.*;

@Service
public class StudentImpl implements StudentService {
    
    private StudentRepository studentRepository;

    public StudentImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentModel> students = studentRepository.findAll();
        return students.stream().map(student -> mapToDto(student)).collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(UUID id) {
        return mapToDto(studentRepository.findById(id).get());
    }

    @Override
    public StudentModel saveStudent(StudentDto studentDto) {
        return studentRepository.save(mapToModel(studentDto));
    }

    @Override
    public void updateStudent(StudentDto studentDto) {
        studentRepository.save(mapToModel(studentDto));
    }
    
    @Override
    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }
}
