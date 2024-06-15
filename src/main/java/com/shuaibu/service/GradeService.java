package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;

public interface GradeService {
    List<GradeDto> getAllGrades();
    GradeDto getGradeById(UUID id);
    GradeModel saveGrade(GradeDto gradeDto);
    void updateGrade(GradeDto gradeDto);
    void deleteGrade(UUID id);
}
