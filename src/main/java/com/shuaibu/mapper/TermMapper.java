package com.shuaibu.mapper;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;

public class TermMapper {
    
    public static TermDto mapToDto(TermModel termModel){

        return TermDto.builder()
        .id(termModel.getId())
        .termName(termModel.getTermName())
        .build();
    }

    public static TermModel mapToModel(TermDto termDto){

        return TermModel.builder()
        .id(termDto.getId())
        .termName(termDto.getTermName())
        .build();
    }
}
