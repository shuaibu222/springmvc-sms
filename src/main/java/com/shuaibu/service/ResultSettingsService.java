package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.ResultSettingsDto;

public interface ResultSettingsService {
    List<ResultSettingsDto> getAllResultSettings();
    ResultSettingsDto getResultSettingById(Long id);
    void saveOrUpdateResultSetting(ResultSettingsDto resultSettingsDto);
    void deleteResultSetting(Long id);
}
