package com.shuaibu.mapper;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;

public class ResultSettingsMapper {
    
    public static ResultSettingsDto mapToDto(ResultSettingsModel resultSettingsModel){

        return ResultSettingsDto.builder()
        .id(resultSettingsModel.getId())
        .sectionId(resultSettingsModel.getSectionId())
        .firstCA(resultSettingsModel.getFirstCA())
        .secondCA(resultSettingsModel.getSecondCA())
        .thirdCA(resultSettingsModel.getThirdCA())
        .fourthCA(resultSettingsModel.getFourthCA())
        .exam(resultSettingsModel.getExam())
        .total(resultSettingsModel.getTotal())
        .build();
    }

    public static ResultSettingsModel mapToModel(ResultSettingsDto resultSettingDto){

        return ResultSettingsModel.builder()
        .id(resultSettingDto.getId())
        .sectionId(resultSettingDto.getSectionId())
        .firstCA(resultSettingDto.getFirstCA())
        .secondCA(resultSettingDto.getSecondCA())
        .thirdCA(resultSettingDto.getThirdCA())
        .fourthCA(resultSettingDto.getFourthCA())
        .exam(resultSettingDto.getExam())
        .total(resultSettingDto.getTotal())
        .build();
    }
}
