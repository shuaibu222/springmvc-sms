package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;

public interface SchoolClassService {
    List<SchoolClassDto> getAllSchoolClass();
    SchoolClassDto getSchoolClassById(Long id);
    SchoolClassModel saveOrUpdateSchoolClass(SchoolClassDto schoolClassDto);
    void deleteSchoolClass(Long id);
}
