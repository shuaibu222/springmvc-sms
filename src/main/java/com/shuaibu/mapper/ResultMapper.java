package com.shuaibu.mapper;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;

public class ResultMapper {
    
    public static ResultDto mapToDto(ResultModel resultModel){

        return ResultDto.builder()
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
                .total(resultModel.getTotal())
                .grade(resultModel.getGrade())
                .remark(resultModel.getRemark())
                .build();
    }

    public static ResultModel mapToModel(ResultDto resultDto){

        return ResultModel.builder()
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
                .total(resultDto.getTotal())
                .grade(resultDto.getGrade())
                .remark(resultDto.getRemark())
                .build();
    }
}
