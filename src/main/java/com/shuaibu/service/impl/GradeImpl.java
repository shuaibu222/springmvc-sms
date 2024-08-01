package com.shuaibu.service.impl;

import com.shuaibu.mapper.GradeMapper;
import com.shuaibu.model.SectionModel;
import com.shuaibu.repository.SectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;
import com.shuaibu.repository.GradeRepository;
import com.shuaibu.service.GradeService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.GradeMapper.*;

@Service
public class GradeImpl implements GradeService {

    private final SectionRepository sectionRepository;
    private final GradeRepository gradeRepository;

    public GradeImpl(SectionRepository sectionRepository, GradeRepository gradeRepository) {
        this.sectionRepository = sectionRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<GradeDto> getAllGrades() {
        List<GradeModel> grades = gradeRepository.findAll();
        return grades.stream().map(GradeMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GradeDto getGradeById(Long id) {
        return mapToDto(gradeRepository.findById(id).orElseThrow());
    }

    @Override
    public void saveGrade(GradeDto gradeDto) {

        GradeModel gradeModel = mapToModel(gradeDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(gradeDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + gradeDto.getSectionId()));
        // Set fetched entities to resultSetting model
        gradeModel.setSectionId(section.getSectionName());

        gradeRepository.save(gradeModel);
    }

    @Override
    public void updateGrade(GradeDto gradeDto) {
        GradeModel gradeModel = mapToModel(gradeDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(gradeDto.getSectionId()))
                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + gradeDto.getSectionId()));
        // Set fetched entities to resultSetting model
        gradeModel.setSectionId(section.getSectionName());

        gradeRepository.save(gradeModel);
    }
    
    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}
