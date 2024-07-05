package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SchoolClassDto;

public interface SchoolClassService {
    List<SchoolClassDto> getAllSchoolClass();
    SchoolClassDto getSchoolClassById(Long id);
    void saveOrUpdateSchoolClass(SchoolClassDto schoolClassDto);
    void deleteSchoolClass(Long id);
}
