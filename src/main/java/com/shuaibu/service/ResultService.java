package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;

public interface ResultService {
    List<ResultDto> getAllResults();
    ResultDto getResultById(Long id);
    ResultModel saveOrUpdateResult(ResultDto resultDto);
    void deleteResult(Long id);
}
