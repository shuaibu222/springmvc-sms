package com.shuaibu.service.impl;

import com.shuaibu.dto.ClassTeacherDto;
import com.shuaibu.mapper.ClassTeacherCommentMapper;
import com.shuaibu.repository.ClassTeacherRepository;
import com.shuaibu.service.ClassTeacherService;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.model.ClassTeacherCommentModel;
import com.shuaibu.repository.ClassTeacherCommentRepository;
import com.shuaibu.service.ClassTeacherCommentService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ClassTeacherCommentMapper.*;

@Service
public class ClassTeacherCommentImpl implements ClassTeacherCommentService {
    
    private final ClassTeacherCommentRepository classTeacherCommentRepository;
    private final ClassTeacherService classTeacherService;

    public ClassTeacherCommentImpl(ClassTeacherCommentRepository classTeachercommentRepository, ClassTeacherRepository classTeacherRepository, ClassTeacherService classTeacherService) {
        this.classTeacherCommentRepository = classTeachercommentRepository;
        this.classTeacherService = classTeacherService;
    }

    @Override
    public List<ClassTeacherCommentDto> getAllClassTeacherComments() {
        List<ClassTeacherCommentModel> classTeacherComments = classTeacherCommentRepository.findAll();
        return classTeacherComments.stream().map(ClassTeacherCommentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ClassTeacherCommentDto getClassTeacherCommentById(Long id) {
        return mapToDto(classTeacherCommentRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateClassTeacherComment(ClassTeacherCommentDto classTeacherCommentDto) {

            ClassTeacherDto classTeacherModel = classTeacherService.getClassTeacherById(Long.valueOf(classTeacherCommentDto.getTeacherId()));

            classTeacherCommentDto.setClassName(classTeacherModel.getClassName());
            classTeacherCommentDto.setClassId(classTeacherModel.getClassId());
            classTeacherCommentDto.setTeacherName(classTeacherModel.getTeacherName());
            classTeacherCommentDto.setTeacherId(classTeacherModel.getTeacherId());

            classTeacherCommentRepository.save(mapToModel(classTeacherCommentDto));
    }
    
    @Override
    public void deleteClassTeacherComment(Long id) {
        classTeacherCommentRepository.deleteById(id);
    }
}
