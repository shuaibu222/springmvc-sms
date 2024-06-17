package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;
import com.shuaibu.repository.GradeRepository;
import com.shuaibu.service.GradeService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.GradeMapper.*;

@Service
public class GradeImpl implements GradeService {
    
    private GradeRepository gradeRepository;

    public GradeImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<GradeDto> getAllGrades() {
        List<GradeModel> grades = gradeRepository.findAll();
        return grades.stream().map(grade -> mapToDto(grade)).collect(Collectors.toList());
    }

    @Override
    public GradeDto getGradeById(Long id) {
        return mapToDto(gradeRepository.findById(id).get());
    }

    @Override
    public GradeModel saveGrade(GradeDto gradeDto) {
        return gradeRepository.save(mapToModel(gradeDto));
    }

    @Override
    public void updateGrade(GradeDto gradeDto) {
        gradeRepository.save(mapToModel(gradeDto));
    }
    
    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }
}
