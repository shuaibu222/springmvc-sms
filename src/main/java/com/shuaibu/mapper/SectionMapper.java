package com.shuaibu.mapper;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;

public class SectionMapper {
    
    public static SectionDto mapToDto(SectionModel sectionModel){

        return SectionDto.builder()
                .id(sectionModel.getId())
                .sectionName(sectionModel.getSectionName())
                .sectionDesc(sectionModel.getSectionDesc())
                .headTeacherId(sectionModel.getHeadTeacherId())
                .classIds(sectionModel.getClassIds())
        .build();
    }

    public static SectionModel mapToModel(SectionDto sectionDto){

        return SectionModel.builder()
                .id(sectionDto.getId())
                .sectionName(sectionDto.getSectionName())
                .sectionDesc(sectionDto.getSectionDesc())
                .headTeacherId(sectionDto.getHeadTeacherId())
                .classIds(sectionDto.getClassIds())
        .build();
    }
}
