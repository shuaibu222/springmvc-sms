package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SubjectDto;

public interface SubjectService {
    List<SubjectDto> getAllSubjects();
    SubjectDto getSubjectById(Long id);
    void saveSubject(SubjectDto subjectDto);
    void updateSubject(SubjectDto subjectDto);
    void deleteSubject(Long id);
}
