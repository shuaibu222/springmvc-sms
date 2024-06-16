package com.shuaibu.mapper;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;

public class ResultMapper {
    
    public static ResultDto mapToDto(ResultModel resultModel){
        ResultDto resultDto = ResultDto.builder()
        .id(resultModel.getId())
        .name(resultModel.getName())
        .section(resultModel.getSection())
        .regNo(resultModel.getRegNo())
        .academicSession(resultModel.getAcademicSession())
        .studentClass(resultModel.getStudentClass())
        .term(resultModel.getTerm())
        .firstCA(resultModel.getFirstCA())
        .secondCA(resultModel.getSecondCA())
        .studentClass(resultModel.getStudentClass())
        .term(resultModel.getTerm())
        .exam(resultModel.getExam())
        .subject(resultModel.getSubject())
        .build();

        return resultDto;
    }

    public static ResultModel mapToModel(ResultDto resultDto){
        ResultModel resultModel = ResultModel.builder()
        .id(resultDto.getId())
        .name(resultDto.getName())
        .section(resultDto.getSection())
        .regNo(resultDto.getRegNo())
        .academicSession(resultDto.getAcademicSession())
        .studentClass(resultDto.getStudentClass())
        .term(resultDto.getTerm())
        .firstCA(resultDto.getFirstCA())
        .secondCA(resultDto.getSecondCA())
        .studentClass(resultDto.getStudentClass())
        .term(resultDto.getTerm())
        .exam(resultDto.getExam())
        .subject(resultDto.getSubject())
        .build();

        return resultModel;
    }
}
