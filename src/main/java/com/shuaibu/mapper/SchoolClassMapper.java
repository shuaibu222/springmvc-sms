package com.shuaibu.mapper;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;

import java.util.ArrayList;

public class SchoolClassMapper {
    
    public static SchoolClassDto mapToDto(SchoolClassModel schoolClassModel){
        SchoolClassDto schoolClassDto = SchoolClassDto.builder()
                .id(schoolClassModel.getId())
                .sectionId(schoolClassModel.getSectionId())
                .className(schoolClassModel.getClassName())
                .staffModels(schoolClassModel.getStaffModels())
                .subjectModels(schoolClassModel.getSubjectModels())
                .build();

        return schoolClassDto;
    }

    public static SchoolClassModel mapToModel(SchoolClassDto schoolClassDto){
        SchoolClassModel schoolClassModel =SchoolClassModel.builder()
                .id(schoolClassDto.getId())
                .sectionId(schoolClassDto.getSectionId())
                .className(schoolClassDto.getClassName())
                .staffModels(schoolClassDto.getStaffModels())
                .subjectModels(schoolClassDto.getSubjectModels())
                .build();

        return schoolClassModel;
    }
}
