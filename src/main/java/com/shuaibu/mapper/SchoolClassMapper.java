package com.shuaibu.mapper;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;

public class SchoolClassMapper {
    
    public static SchoolClassDto mapToDto(SchoolClassModel schoolClassModel){
        SchoolClassDto schoolClassDto = SchoolClassDto.builder()
        .id(schoolClassModel.getId())
        .sectionName(schoolClassModel.getSectionName())
        .className(schoolClassModel.getClassName())
        .build();

        return schoolClassDto;
    }

    public static SchoolClassModel mapToModel(SchoolClassDto schoolClassDto){
        SchoolClassModel schoolClassModel =SchoolClassModel.builder()
        .id(schoolClassDto.getId())
        .sectionName(schoolClassDto.getSectionName())
        .className(schoolClassDto.getClassName())
        .build();

        return schoolClassModel;
    }
}
