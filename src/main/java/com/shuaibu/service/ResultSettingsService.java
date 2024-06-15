package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;

public interface ResultSettingsService {
    List<ResultSettingsDto> getAllResultSettings();
    ResultSettingsDto getResultSettingById(UUID id);
    ResultSettingsModel saveResultSetting(ResultSettingsDto resultSettingsDto);
    void updateResultSetting(ResultSettingsDto resultSettingsDto);
    void deleteResultSetting(UUID id);
}
