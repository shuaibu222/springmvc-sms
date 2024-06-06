package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.service.SectionService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SectionMapper.*;

@Service
public class SectionImpl implements SectionService {
    
    private SectionRepository sectionRepository;

    public SectionImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<SectionDto> getAllSections() {
        List<SectionModel> sections = sectionRepository.findAll();
        return sections.stream().map(section -> mapToDto(section)).collect(Collectors.toList());
    }

    @Override
    public SectionDto getSectionById(Long id) {
        return mapToDto(sectionRepository.findById(id).get());
    }

    @Override
    public SectionModel saveSection(SectionDto sectionDto) {
        return sectionRepository.save(mapToModel(sectionDto));
    }

    @Override
    public void updateSection(SectionDto sectionDto) {
        sectionRepository.save(mapToModel(sectionDto));
    }
    
    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
