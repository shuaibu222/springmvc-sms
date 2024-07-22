package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SectionDto;

public interface SectionService {
    List<SectionDto> getAllSections();
    SectionDto getSectionById(Long id);
    void saveOrUpdateSection(SectionDto sectionDto);
    void deleteSection(Long id);
}
