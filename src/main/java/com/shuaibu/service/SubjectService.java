package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;

public interface SubjectService {
    List<SubjectDto> getAllSubjects();
    SubjectDto getSubjectById(Long id);
    SubjectModel saveSubject(SubjectDto subjectDto);
    void updateSubject(SubjectDto subjectDto);
    void deleteSubject(Long id);
}
