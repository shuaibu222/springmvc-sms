package com.shuaibu.service.impl;

import com.shuaibu.model.SectionModel;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.service.SectionService;
import jakarta.persistence.EntityNotFoundException;
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
    private SectionRepository sectionRepository;
    private SectionService sectionService;

    public SchoolClassImpl(SchoolClassRepository schoolClassRepository, SectionRepository sectionRepository, SectionService sectionService) {
        this.schoolClassRepository = schoolClassRepository;
        this.sectionRepository = sectionRepository;
        this.sectionService = sectionService;
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
    public SchoolClassModel saveOrUpdateSchoolClass(SchoolClassDto schoolClassDto) {

        SectionModel section = sectionRepository.findById(Long.valueOf(schoolClassDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + schoolClassDto.getSectionId()));

        schoolClassDto.setSectionId(section.getSectionName());

        SchoolClassModel schoolClassModel = schoolClassRepository.save(mapToModel(schoolClassDto));

        // add class to section
        section.getClassIds().add(schoolClassModel.getId().toString());
        sectionRepository.save(section);


        return schoolClassModel;
    }
    
    @Override
    public void deleteSchoolClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}
