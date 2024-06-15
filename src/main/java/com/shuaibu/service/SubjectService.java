package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;

public interface SubjectService {
    List<SubjectDto> getAllSubjects();
    SubjectDto getSubjectById(UUID id);
    SubjectModel saveSubject(SubjectDto subjectDto);
    void updateSubject(SubjectDto subjectDto);
    void deleteSubject(UUID id);
}
