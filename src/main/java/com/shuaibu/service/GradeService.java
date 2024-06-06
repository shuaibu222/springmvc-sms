package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;

public interface GradeService {
    List<GradeDto> getAllGrades();
    GradeDto getGradeById(Long id);
    GradeModel saveGrade(GradeDto GradeDto);
    void updateGrade(GradeDto GradeDto);
    void deleteGrade(Long id);
}
