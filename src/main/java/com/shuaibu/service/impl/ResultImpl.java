package com.shuaibu.service.impl;

import com.shuaibu.controller.GlobalControllerAdvice;
import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.mapper.ResultMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.ResultSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.dto.ResultDto;
import com.shuaibu.service.GradeService;
import com.shuaibu.service.ResultService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultMapper.*;

@Service
public class ResultImpl implements ResultService {

    private static final Logger logger = LoggerFactory.getLogger(ResultImpl.class);
    private final ResultRepository resultRepository;
    private final SectionRepository sectionRepository;
    private final SessionRepository sessionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final SubjectRepository subjectRepository;
    private final GradeService gradeService;
    private final ResultSettingsService resultSettingsService;
    private final StudentRepository studentRepository;


    public ResultImpl(ResultRepository resultRepository, SectionRepository sectionRepository,
                      SessionRepository sessionRepository, SchoolClassRepository schoolClassRepository,
                      TermRepository termRepository, SubjectRepository subjectRepository, GradeService gradeService, ResultSettingsService resultSettingsService, StudentRepository studentRepository) {
        this.resultRepository = resultRepository;
        this.sectionRepository = sectionRepository;
        this.sessionRepository = sessionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.resultSettingsService = resultSettingsService;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<ResultDto> getAllResults() {
        List<ResultModel> results = resultRepository.findAll();
        return results.stream().map(ResultMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ResultDto getResultById(Long id) {
        return mapToDto(resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with ID: " + id)));
    }

    @Override
    public void saveOrUpdateResult(ResultDto resultDto) {

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

        StudentModel student = studentRepository.findById(Long.parseLong(resultDto.getName()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + resultDto.getName()));

        List<ResultSettingsDto> resultSettingsDto = resultSettingsService.getAllResultSettings();
        ResultSettingsDto resultSetting = resultSettingsDto.stream()
                .filter(rs -> rs.getSectionId().equals(section.getSectionName()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Result settings not found for section ID: " + section.getId()));

        // Verify scores against result settings
        if (resultDto.getFirstCA() > Long.parseLong(resultSetting.getFirstCA())) {
            throw new IllegalArgumentException("First CA score exceeds the allowed maximum of " + resultSetting.getFirstCA());
        }
        if (resultDto.getSecondCA() > Long.parseLong(resultSetting.getSecondCA())) {
            throw new IllegalArgumentException("Second CA score exceeds the allowed maximum of " + resultSetting.getSecondCA());
        }
        if (resultDto.getExam() > Long.parseLong(resultSetting.getExam())) {
            throw new IllegalArgumentException("Exam score exceeds the allowed maximum of " + resultSetting.getExam());
        }

        // Map ResultDto to ResultModel
        ResultModel resultModel = mapToModel(resultDto);

        // Set fetched entities to result model
        resultModel.setSectionId(section.getSectionName());
        resultModel.setAcademicSessionId(academicSession.getSessionName());
        resultModel.setStudentClassId(schoolClass.getClassName());
        resultModel.setTermId(term.getTermName());
        resultModel.setSubjectId(subject.getSubjectName());
        resultModel.setName(student.getFirstName() + ' ' + student.getLastName());
        resultModel.setRegNo(student.getRegNo());

        // Calculating total
        Integer totalMark = resultModel.getFirstCA() + resultModel.getSecondCA() + resultModel.getExam();
        resultModel.setTotal(totalMark);


        // Find the matching GradeDto based on total score
        List<GradeDto> gradeList = gradeService.getAllGrades();
        GradeDto matchingGrade = gradeList.stream()
                .filter(grade -> totalMark >= grade.getRangeFrom() && totalMark <= grade.getRangeTo())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No grade found for total marks: " + totalMark));


        resultModel.setGrade(matchingGrade.getGrade());
        resultModel.setRemark(matchingGrade.getRemark());

        // Save the result model
        resultRepository.save(resultModel);
    }
    
    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    @Override
    public List<ResultModel> getResultModelsBySectionIdAndStudentClassId(String section, String classId) {
        return resultRepository.findResultModelsBySectionIdAndStudentClassId(section, classId);
    }
}
