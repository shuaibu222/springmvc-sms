package com.shuaibu.mapper;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;

public class TermMapper {
    
    public static TermDto mapToDto(TermModel termModel){
        TermDto termDto = TermDto.builder()
        .id(termModel.getId())
        .termName(termModel.getTermName())
        .build();

        return termDto;
    }

    public static TermModel mapToModel(TermDto termDto){
        TermModel termModel =TermModel.builder()
        .id(termDto.getId())
        .termName(termDto.getTermName())
        .build();

        return termModel;
    }
}
