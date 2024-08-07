package com.shuaibu.service.impl;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.dto.SessionDto;
import com.shuaibu.dto.TermDto;
import com.shuaibu.mapper.ResultMapper;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.ResultSettingsService;
import com.shuaibu.service.SessionService;
import com.shuaibu.service.TermService;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.dto.GradeDto;
import com.shuaibu.dto.HeadTeacherCommentDto;
import com.shuaibu.dto.ResultDto;
import com.shuaibu.service.ClassTeacherCommentService;
import com.shuaibu.service.GradeService;
import com.shuaibu.service.HeadTeacherCommentService;
import com.shuaibu.service.ResultService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.ResultMapper.*;

@Service
public class ResultImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final SectionRepository sectionRepository;
    private final SessionRepository sessionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TermRepository termRepository;
    private final SubjectRepository subjectRepository;
    private final GradeService gradeService;
    private final ResultSettingsService resultSettingsService;
    private final StudentRepository studentRepository;
    private final ReportSheetRepository reportSheetRepository;
    private final TermService termService;
    private final SessionService sessionService;
    private final ClassTeacherCommentService classTeacherCommentService;
    private final HeadTeacherCommentService headTeacherCommentService;


    public ResultImpl(ResultRepository resultRepository, SectionRepository sectionRepository,
                    SessionRepository sessionRepository, SchoolClassRepository schoolClassRepository,
                    TermRepository termRepository, SubjectRepository subjectRepository, GradeService gradeService, ResultSettingsService resultSettingsService, StudentRepository studentRepository, ReportSheetRepository reportSheetRepository, SessionService sessionService, TermService termService, ClassTeacherCommentService classTeacherCommentService, HeadTeacherCommentService headTeacherCommentService) {
        this.resultRepository = resultRepository;
        this.sectionRepository = sectionRepository;
        this.sessionRepository = sessionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.resultSettingsService = resultSettingsService;
        this.studentRepository = studentRepository;
        this.reportSheetRepository = reportSheetRepository;
        this.termService = termService;
        this.sessionService = sessionService;
        this.classTeacherCommentService = classTeacherCommentService;
        this.headTeacherCommentService = headTeacherCommentService;
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
        if (resultDto.getFirstCA() > resultSetting.getFirstCA()) {
            throw new IllegalArgumentException("First CA score exceeds the allowed maximum of " + resultSetting.getFirstCA());
        }
        if (resultDto.getSecondCA() > resultSetting.getSecondCA()) {
            throw new IllegalArgumentException("Second CA score exceeds the allowed maximum of " + resultSetting.getSecondCA());
        }
        if (resultDto.getExam() > resultSetting.getExam()) {
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
                .filter(grade -> totalMark >= grade.getRangeFrom() && totalMark <= grade.getRangeTo() && section.getSectionName().equals(grade.getSectionId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No grade found for total marks: " + totalMark));


        resultModel.setGrade(matchingGrade.getGrade());
        resultModel.setRemark(matchingGrade.getRemark());

        // Save the result model
        ResultModel result = resultRepository.save(resultModel);

        // pass the regNo
        generateReportSheet(result.getRegNo());
    }

    @Override
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }


    @Override
    public List<ResultModel> getResultModelsByStudentClassIdAndTermIdAndAcademicSessionId(String classId, String termId, String academicSessionId) {
        return resultRepository.findResultModelsByStudentClassIdAndTermIdAndAcademicSessionId(classId, termId, academicSessionId);
    }

    private synchronized void generateReportSheet(String regNo) {

        String activeSessionName = sessionService.getAllSessions().stream()
                .filter(s -> s.getIsActive().equals("True"))
                .findFirst()
                .map(SessionDto::getSessionName)
                .orElseThrow(() -> new IllegalStateException("No active session found"));

        String activeTermName = termService.getAllTerms().stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .map(TermDto::getTermName)
                .orElseThrow(() -> new IllegalStateException("No active term found"));

        ReportSheetModel reportSheetModel = reportSheetRepository.findByRegNoAndTermAndAcademicSession(regNo, activeTermName, activeSessionName);

        // for new report sheet model
        if (reportSheetModel == null) {
            ResultModel resultModel = resultRepository.findOneByRegNoAndTermIdAndAcademicSessionId(regNo, activeTermName, activeSessionName);

            // count number of students in class
            List<StudentModel> students = studentRepository.findByStudentClassNameAndIsActive(resultModel.getStudentClassId(), "True");
            Long noOfStudents = students.stream().count();

            // generate new report sheet
            ReportSheetModel reportSheet = new ReportSheetModel();
            reportSheet.setName(resultModel.getName());
            reportSheet.setAcademicSession(resultModel.getAcademicSessionId());
            reportSheet.setRegNo(resultModel.getRegNo());
            reportSheet.setStudentClass(resultModel.getStudentClassId());
            reportSheet.setTerm(resultModel.getTermId());
            reportSheet.setSectionName(resultModel.getSectionId());
            reportSheet.setNoOfStudents(String.valueOf(noOfStudents));
            reportSheet.setSignature("signature");
            reportSheet.setTermEnding("2024-01-01");
            reportSheet.setNextTermBegins("2025-01-01");
            reportSheet.setTotalMarks(resultModel.getTotal() + 0);
            reportSheet.setResults(Set.of(resultModel.getId()));

            reportSheetRepository.save(reportSheet);

        } else {

            // for existing report sheet
            List<ResultModel> resultModels = resultRepository.findAllByRegNoAndTermIdAndAcademicSessionId(regNo, activeTermName, activeSessionName);
            System.out.println(resultModels);

            int trackRes = 0; // track total of individual student results

            // add with the new balance
            for (ResultModel rModel : resultModels) {
                trackRes = trackRes + rModel.getTotal();
                // add result id to report sheet
                reportSheetModel.getResults().add(rModel.getId());
            }

            reportSheetModel.setTotalMarks(trackRes);

            // for commenting purposes only
            ReportSheetModel savedReportSheet = reportSheetRepository.save(reportSheetModel);

            // teacher commenting
            List<ClassTeacherCommentDto> classTeacherComments = classTeacherCommentService.getAllClassTeacherComments();
            ClassTeacherCommentDto classTeacherComment = classTeacherComments.stream()
                                .filter(ctc -> ctc.getClassName().equals(savedReportSheet.getStudentClass()) && savedReportSheet.getTotalMarks() >= ctc.getRangeFrom()  && savedReportSheet.getTotalMarks() <= ctc.getRangeTo())
                                .findFirst()
                                .orElseThrow();

            savedReportSheet.setClassTeacherComment(classTeacherComment.getRemark());

            // head teacher commenting
            List<HeadTeacherCommentDto> headTeacherComments = headTeacherCommentService.getAllHeadTeacherComments();
            HeadTeacherCommentDto headTeacherComment = headTeacherComments.stream()
                                .filter(ctc -> ctc.getSectionName().equals(savedReportSheet.getSectionName()) && savedReportSheet.getTotalMarks() >= ctc.getRangeFrom()  && savedReportSheet.getTotalMarks() <= ctc.getRangeTo())
                                .findFirst()
                                .orElseThrow();

            savedReportSheet.setHeadTeacherComment(headTeacherComment.getRemark());

            // save it again after commenting is done!
            reportSheetRepository.save(reportSheetModel);
        }
    }
}
