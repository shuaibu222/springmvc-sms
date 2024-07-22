package com.shuaibu.service.impl;

import com.shuaibu.mapper.ClassTeacherCommentMapper;
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
    
    private final ClassTeacherCommentRepository ClassTeacherCommentRepository;

    public ClassTeacherCommentImpl(ClassTeacherCommentRepository ClassTeachercommentRepository) {
        this.ClassTeacherCommentRepository = ClassTeachercommentRepository;
    }

    @Override
    public List<ClassTeacherCommentDto> getAllClassTeacherComments() {
        List<ClassTeacherCommentModel> ClassTeacherComments = ClassTeacherCommentRepository.findAll();
        return ClassTeacherComments.stream().map(ClassTeacherCommentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ClassTeacherCommentDto getClassTeacherCommentById(Long id) {
        return mapToDto(ClassTeacherCommentRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveOrUpdateClassTeacherComment(ClassTeacherCommentDto ClassTeachercommentDto) {
        ClassTeacherCommentRepository.save(mapToModel(ClassTeachercommentDto));
    }

    
    @Override
    public void deleteClassTeacherComment(Long id) {
        ClassTeacherCommentRepository.deleteById(id);
    }
}
