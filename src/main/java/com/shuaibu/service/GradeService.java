package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.GradeDto;

public interface GradeService {
    List<GradeDto> getAllGrades();
    GradeDto getGradeById(Long id);
    void saveGrade(GradeDto gradeDto);
    void updateGrade(GradeDto gradeDto);
    void deleteGrade(Long id);
}
