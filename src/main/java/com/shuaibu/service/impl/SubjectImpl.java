package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.repository.SubjectRepository;
import com.shuaibu.service.SubjectService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SubjectMapper.*;

@Service
public class SubjectImpl implements SubjectService {
    
    private SubjectRepository subjectRepository;

    public SubjectImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        List<SubjectModel> subjects = subjectRepository.findAll();
        return subjects.stream().map(subject -> mapToDto(subject)).collect(Collectors.toList());
    }

    @Override
    public SubjectDto getSubjectById(UUID id) {
        return mapToDto(subjectRepository.findById(id).get());
    }

    @Override
    public SubjectModel saveSubject(SubjectDto subjectDto) {
        return subjectRepository.save(mapToModel(subjectDto));
    }

    @Override
    public void updateSubject(SubjectDto subjectDto) {
        subjectRepository.save(mapToModel(subjectDto));
    }
    
    @Override
    public void deleteSubject(UUID id) {
        subjectRepository.deleteById(id);
    }
}
