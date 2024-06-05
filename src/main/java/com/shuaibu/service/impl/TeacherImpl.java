package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.TeacherDto;
import com.shuaibu.model.TeacherModel;
import com.shuaibu.repository.TeacherRepository;
import com.shuaibu.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.TeacherMapper.*;

@Service
public class TeacherImpl implements TeacherService {
    
    private TeacherRepository teacherRepository;

    public TeacherImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<TeacherModel> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacher -> mapToDto(teacher)).collect(Collectors.toList());
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        return mapToDto(teacherRepository.findById(id).get());
    }

    @Override
    public TeacherModel saveTeacher(TeacherDto teacherDto) {
        return teacherRepository.save(mapToModel(teacherDto));
    }

    @Override
    public void updateTeacher(TeacherDto teacherDto) {
        teacherRepository.save(mapToModel(teacherDto));
    }
    
    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
