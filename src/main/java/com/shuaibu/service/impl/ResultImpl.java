package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;
import com.shuaibu.repository.ResultRepository;
import com.shuaibu.service.ResultService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultMapper.*;

@Service
public class ResultImpl implements ResultService {
    
    private ResultRepository resultRepository;

    public ResultImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public List<ResultDto> getAllResults() {
        List<ResultModel> grades = resultRepository.findAll();
        return grades.stream().map(grade -> mapToDto(grade)).collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultById(UUID id) {
        return mapToDto(resultRepository.findById(id).get());
    }

    @Override
    public ResultModel saveResult(ResultDto gradeDto) {
        return resultRepository.save(mapToModel(gradeDto));
    }

    @Override
    public void updateResult(ResultDto gradeDto) {
        resultRepository.save(mapToModel(gradeDto));
    }
    
    @Override
    public void deleteResult(UUID id) {
        resultRepository.deleteById(id);
    }
}
