package com.shuaibu.mapper;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;

public class SchoolClassMapper {
    
    public static SchoolClassDto mapToDto(SchoolClassModel schoolClassModel){

        return SchoolClassDto.builder()
                .id(schoolClassModel.getId())
                .className(schoolClassModel.getClassName())
                .sectionId(schoolClassModel.getSectionId())
                .subjectModels(schoolClassModel.getSubjectModels())
                .staffModels(schoolClassModel.getStaffModels())
                .build();
    }

    public static SchoolClassModel mapToModel(SchoolClassDto schoolClassDto){

        return SchoolClassModel.builder()
                .id(schoolClassDto.getId())
                .className(schoolClassDto.getClassName())
                .sectionId(schoolClassDto.getSectionId())
                .subjectModels(schoolClassDto.getSubjectModels())
                .staffModels(schoolClassDto.getStaffModels())
                .build();
    }
}
