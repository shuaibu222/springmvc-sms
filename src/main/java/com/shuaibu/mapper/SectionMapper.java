package com.shuaibu.mapper;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;

public class SectionMapper {
    
    public static SectionDto mapToDto(SectionModel sectionModel){
        SectionDto sectionDto = SectionDto.builder()
        .id(sectionModel.getId())
        .sectionName(sectionModel.getSectionName())
        .build();

        return sectionDto;
    }

    public static SectionModel mapToModel(SectionDto sectionDto){
        SectionModel sectionModel = SectionModel.builder()
        .id(sectionDto.getId())
        .sectionName(sectionDto.getSectionName())
        .build();

        return sectionModel;
    }
}
