package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;

public interface SchoolClassService {
    List<SchoolClassDto> getAllSchoolClass();
    SchoolClassDto getSchoolClassById(UUID id);
    SchoolClassModel saveSchoolClass(SchoolClassDto schoolClassDto);
    void updateSchoolClass(SchoolClassDto schoolClassDto);
    void deleteSchoolClass(UUID id);
}
