package com.shuaibu.mapper;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;

public class SubjectMapper {
    
    public static SubjectDto mapToDto(SubjectModel subjectModel){

        return SubjectDto.builder()
        .id(subjectModel.getId())
        .subjectName(subjectModel.getSubjectName())
        .abbr(subjectModel.getAbbr())
        .build();
    }

    public static SubjectModel mapToModel(SubjectDto subjectDto){

        return SubjectModel.builder()
        .id(subjectDto.getId())
        .subjectName(subjectDto.getSubjectName())
        .abbr(subjectDto.getAbbr())
        .build();
    }
}
