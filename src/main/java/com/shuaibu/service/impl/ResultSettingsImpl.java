package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;
import com.shuaibu.repository.ResultSettingsRepository;
import com.shuaibu.service.ResultSettingsService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultSettingsMapper.*;

@Service
public class ResultSettingsImpl implements ResultSettingsService {
    
    private ResultSettingsRepository resultSettingsRepository;

    public ResultSettingsImpl(ResultSettingsRepository resultSettingsRepository) {
        this.resultSettingsRepository = resultSettingsRepository;
    }

    @Override
    public List<ResultSettingsDto> getAllResultSettings() {
        List<ResultSettingsModel> resultSetting = resultSettingsRepository.findAll();
        return resultSetting.stream().map(grade -> mapToDto(grade)).collect(Collectors.toList());
    }

    @Override
    public ResultSettingsDto getResultSettingById(UUID id) {
        return mapToDto(resultSettingsRepository.findById(id).get());
    }

    @Override
    public ResultSettingsModel saveResultSetting(ResultSettingsDto resultSettingsDto) {
        return resultSettingsRepository.save(mapToModel(resultSettingsDto));
    }

    @Override
    public void updateResultSetting(ResultSettingsDto resultSettingsDto) {
        resultSettingsRepository.save(mapToModel(resultSettingsDto));
    }
    
    @Override
    public void deleteResultSetting(UUID id) {
        resultSettingsRepository.deleteById(id);
    }
}
