package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;

public interface SectionService {
    List<SectionDto> getAllSections();
    SectionDto getSectionById(Long id);
    SectionModel saveSection(SectionDto sectionDto);
    void updateSection(SectionDto sectionDto);
    void deleteSection(Long id);
}
