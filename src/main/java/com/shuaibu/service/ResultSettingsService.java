package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;

public interface ResultSettingsService {
    List<ResultSettingsDto> getAllResultSettings();
    ResultSettingsDto getResultSettingById(Long id);
    ResultSettingsModel saveResultSetting(ResultSettingsDto resultSettingsDto);
    void updateResultSetting(ResultSettingsDto resultSettingsDto);
    void deleteResultSetting(Long id);
}
