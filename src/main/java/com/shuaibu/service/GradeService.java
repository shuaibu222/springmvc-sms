package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;

public interface GradeService {
    List<GradeDto> getAllGrades();
    GradeDto getGradeById(Long id);
    GradeModel saveGrade(GradeDto gradeDto);
    void updateGrade(GradeDto gradeDto);
    void deleteGrade(Long id);
}
