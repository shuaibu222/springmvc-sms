package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;

public interface SectionService {
    List<SectionDto> getAllSections();
    SectionDto getSectionById(UUID id);
    SectionModel saveSection(SectionDto sectionDto);
    void updateSection(SectionDto sectionDto);
    void deleteSection(UUID id);
}
