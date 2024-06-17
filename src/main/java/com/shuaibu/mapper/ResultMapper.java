package com.shuaibu.mapper;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;

public class ResultMapper {
    
    public static ResultDto mapToDto(ResultModel resultModel){
        ResultDto resultDto = ResultDto.builder()
        .id(resultModel.getId())
        .name(resultModel.getName())
        .sectionId(resultModel.getSectionId())
        .regNo(resultModel.getRegNo())
        .academicSessionId(resultModel.getAcademicSessionId())
        .studentClassId(resultModel.getStudentClassId())
        .termId(resultModel.getTermId())
        .firstCA(resultModel.getFirstCA())
        .secondCA(resultModel.getSecondCA())
        .exam(resultModel.getExam())
        .subjectId(resultModel.getSubjectId())
        .build();

        return resultDto;
    }

    public static ResultModel mapToModel(ResultDto resultDto){
        ResultModel resultModel = ResultModel.builder()
        .id(resultDto.getId())
        .name(resultDto.getName())
        .sectionId(resultDto.getSectionId())
        .regNo(resultDto.getRegNo())
        .academicSessionId(resultDto.getAcademicSessionId())
        .studentClassId(resultDto.getStudentClassId())
        .termId(resultDto.getTermId())
        .firstCA(resultDto.getFirstCA())
        .secondCA(resultDto.getSecondCA())
        .exam(resultDto.getExam())
        .subjectId(resultDto.getSubjectId())
        .build();

        return resultModel;
    }
}
