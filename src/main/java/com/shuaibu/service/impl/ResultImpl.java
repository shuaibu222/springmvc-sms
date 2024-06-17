package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.SectionModel;
import com.shuaibu.model.SessionModel;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.model.TermModel;
import com.shuaibu.repository.ResultRepository;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.repository.SessionRepository;
import com.shuaibu.repository.SubjectRepository;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.ResultService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultMapper.*;

@Service
public class ResultImpl implements ResultService {
    
    private ResultRepository resultRepository;
    private SectionRepository sectionRepository;
    private SessionRepository sessionRepository;
    private SchoolClassRepository schoolClassRepository;
    private TermRepository termRepository;
    private SubjectRepository subjectRepository;

    

    public ResultImpl(ResultRepository resultRepository, SectionRepository sectionRepository,
            SessionRepository sessionRepository, SchoolClassRepository schoolClassRepository,
            TermRepository termRepository, SubjectRepository subjectRepository) {
        this.resultRepository = resultRepository;
        this.sectionRepository = sectionRepository;
        this.sessionRepository = sessionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<ResultDto> getAllResults() {
        List<ResultModel> results = resultRepository.findAll();
        return results.stream().map(result -> mapToDto(result)).collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultById(Long id) {
        return mapToDto(resultRepository.findById(id).get());
    }

    @Override
    public ResultModel saveOrUpdateResult(ResultDto resultDto) {
        // Map ResultDto to ResultModel
        ResultModel resultModel = mapToModel(resultDto);

        // Fetch related entities from repositories
        SectionModel section = sectionRepository.findById(Long.parseLong(resultDto.getSectionId()))
                                .orElseThrow(() -> new EntityNotFoundException("Section not found with ID: " + resultDto.getSectionId()));
        
        SessionModel academicSession = sessionRepository.findById(Long.parseLong(resultDto.getAcademicSessionId()))
                                            .orElseThrow(() -> new EntityNotFoundException("Academic Session not found with ID: " + resultDto.getAcademicSessionId()));

        SchoolClassModel schoolClass = schoolClassRepository.findById(Long.parseLong(resultDto.getStudentClassId()))
                                    .orElseThrow(() -> new EntityNotFoundException("School Class not found with ID: " + resultDto.getStudentClassId()));

        TermModel term = termRepository.findById(Long.parseLong(resultDto.getTermId()))
                        .orElseThrow(() -> new EntityNotFoundException("Term not found with ID: " + resultDto.getTermId()));

        SubjectModel subject = subjectRepository.findById(Long.parseLong(resultDto.getSubjectId()))
                            .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + resultDto.getSubjectId()));

        // Set fetched entities to result model
        resultModel.setSectionId(section.getSectionName());
        resultModel.setAcademicSessionId(academicSession.getSessionName());
        resultModel.setStudentClassId(schoolClass.getClassName());
        resultModel.setTermId(term.getTermName());
        resultModel.setSubjectId(subject.getSubjectName());

        // Save the result model
        return resultRepository.save(resultModel);
    }
    
    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}
