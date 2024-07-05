package com.shuaibu.mapper;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;

public class SectionMapper {
    
    public static SectionDto mapToDto(SectionModel sectionModel){

        return SectionDto.builder()
                .id(sectionModel.getId())
                .sectionName(sectionModel.getSectionName())
                .classIds(sectionModel.getClassIds())
        .build();
    }

    public static SectionModel mapToModel(SectionDto sectionDto){

        return SectionModel.builder()
                .id(sectionDto.getId())
                .sectionName(sectionDto.getSectionName())
                .classIds(sectionDto.getClassIds())
        .build();
    }
}
