package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.service.SchoolClassService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SchoolClassMapper.*;

@Service
public class SchoolClassImpl implements SchoolClassService {
    
    private SchoolClassRepository schoolClassRepository;

    public SchoolClassImpl(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public List<SchoolClassDto> getAllSchoolClass() {
        List<SchoolClassModel> sessions = schoolClassRepository.findAll();
        return sessions.stream().map(session -> mapToDto(session)).collect(Collectors.toList());
    }

    @Override
    public SchoolClassDto getSchoolClassById(Long id) {
        return mapToDto(schoolClassRepository.findById(id).get());
    }

    @Override
    public SchoolClassModel saveSchoolClass(SchoolClassDto schoolClassDto) {
        return schoolClassRepository.save(mapToModel(schoolClassDto));
    }

    @Override
    public void updateSchoolClass(SchoolClassDto schoolClassDto) {
        schoolClassRepository.save(mapToModel(schoolClassDto));
    }
    
    @Override
    public void deleteSchoolClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}
