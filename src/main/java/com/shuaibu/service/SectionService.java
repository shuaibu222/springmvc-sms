package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SectionDto;

public interface SectionService {
    List<SectionDto> getAllSections();
    SectionDto getSectionById(Long id);
    void saveSection(SectionDto sectionDto);
    void updateSection(SectionDto sectionDto);
    void deleteSection(Long id);
}
