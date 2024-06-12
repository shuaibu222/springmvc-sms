package com.shuaibu.mapper;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;

public class ResultSettingsMapper {
    
    public static ResultSettingsDto mapToDto(ResultSettingsModel resultSettingsModel){
        ResultSettingsDto resultSettingDto = ResultSettingsDto.builder()
        .id(resultSettingsModel.getId())
        .section(resultSettingsModel.getSection())
        .firstCA(resultSettingsModel.getFirstCA())
        .secondCA(resultSettingsModel.getSecondCA())
        .thirdCA(resultSettingsModel.getThirdCA())
        .fourthCA(resultSettingsModel.getFourthCA())
        .exam(resultSettingsModel.getExam())
        .total(resultSettingsModel.getTotal())
        .build();

        return resultSettingDto;
    }

    public static ResultSettingsModel mapToModel(ResultSettingsDto resultSettingDto){
        ResultSettingsModel resultSettingsModel = ResultSettingsModel.builder()
        .id(resultSettingDto.getId())
        .section(resultSettingDto.getSection())
        .firstCA(resultSettingDto.getFirstCA())
        .secondCA(resultSettingDto.getSecondCA())
        .thirdCA(resultSettingDto.getThirdCA())
        .fourthCA(resultSettingDto.getFourthCA())
        .exam(resultSettingDto.getExam())
        .total(resultSettingDto.getTotal())
        .build();

        return resultSettingsModel;
    }
}
