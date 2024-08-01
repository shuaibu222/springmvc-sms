package com.shuaibu.service.impl;

import com.shuaibu.mapper.SubjectMapper;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.repository.SubjectRepository;
import com.shuaibu.service.SubjectService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SubjectMapper.*;

@Service
public class SubjectImpl implements SubjectService {
    
    private final SubjectRepository subjectRepository;

    public SubjectImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        List<SubjectModel> subjects = subjectRepository.findAll();
        return subjects.stream().map(SubjectMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public SubjectDto getSubjectById(Long id) {
        return mapToDto(subjectRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveSubject(SubjectDto subjectDto) {
        subjectRepository.save(mapToModel(subjectDto));
    }

    @Override
    public void updateSubject(SubjectDto subjectDto) {
        subjectRepository.save(mapToModel(subjectDto));
    }
    
    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
