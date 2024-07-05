package com.shuaibu.controller;

import com.shuaibu.dto.*;
import com.shuaibu.mapper.*;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.SectionModel;
import com.shuaibu.model.StaffModel;
import com.shuaibu.repository.*;
import com.shuaibu.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.model.ResultModel;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/results")
@PreAuthorize("hasRole('STAFF')")
public class ResultController {


    private final StudentService studentService;
    private final ResultService resultService;
    private final SectionService sectionService;
    private final SessionService sessionService;
    private final SchoolClassService schoolClassService;
    private final TermService termService;
    private final SubjectService subjectService;
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
    private final SectionRepository sectionRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SessionRepository sessionRepository;
    private final TermRepository termRepository;
    private final SubjectRepository subjectRepository;


    public ResultController(StudentService studentService, ResultService resultService, SectionService sectionService, SessionService sessionService,
                            SchoolClassService schoolClassService, TermService termService, SubjectService subjectService, StaffRepository staffRepository, UserRepository userRepository, SectionRepository sectionRepository, SchoolClassRepository schoolClassRepository, SessionRepository sessionRepository, TermRepository termRepository, SubjectRepository subjectRepository) {
        this.studentService = studentService;
        this.resultService = resultService;
        this.sectionService = sectionService;
        this.sessionService = sessionService;
        this.schoolClassService = schoolClassService;
        this.termService = termService;
        this.subjectService = subjectService;
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.sessionRepository = sessionRepository;
        this.termRepository = termRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/{sectionName}/{className}")
    public String listResults(@PathVariable("sectionName") String sectionName,
                              @PathVariable("className") String className,
                              Model model) {

        // fetch subject based on teacher id
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Retrieve username directly
            logger.info("Authenticated username: {}", username);

            StaffModel staffModel = staffRepository.findByUserName(username);
            if (staffModel != null) {
                logger.info("Found UserModel: {}", staffModel);

                // Filter classes to find all matching staffClasses
                for (Long subjectId : staffModel.getSubjectModelIds()) {
                    SubjectDto subjectDto = subjectService.getSubjectById(subjectId);
                    subjectDtoList.add(subjectDto);
                }
            } else {
                logger.warn("UserModel not found for username: {}", username);
            }
        } else {
            logger.warn("Authentication is null or not authenticated");
        }

        // Get the specific section and class based on the path variables
        List<SectionModel> filteredSections = sectionRepository.findAll()
                .stream()
                .filter(sec -> sec.getSectionName().equals(sectionName))
                .collect(Collectors.toList());

        List<SchoolClassModel> filteredClasses = schoolClassRepository.findAll()
                .stream()
                .filter(cls -> cls.getSectionId().equals(sectionName) && cls.getClassName().equals(className))
                .collect(Collectors.toList());

        List<StudentDto> filteredStudents = studentService.getAllStudents()
                .stream()
                .filter(cts -> cts.getSectionId().equals(sectionName) && cts.getStudentClassId().equals(className))
                .collect(Collectors.toList());

        // Filter results based on section and classId
        List<ResultModel> filteredResults = resultService.getResultModelsBySectionIdAndStudentClassId(sectionName, className);

        model.addAttribute("students", filteredStudents);
        model.addAttribute("results", filteredResults);
        model.addAttribute("result", new ResultModel());
        model.addAttribute("sections", filteredSections);
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("studentClasses", filteredClasses);
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("subjects", subjectDtoList);

        model.addAttribute("sectionName", sectionName);
        model.addAttribute("className", className);

        return "results/list";
    }

    @GetMapping("/{sectionName}/{className}/new")
    public String createResultForm(@PathVariable("sectionName") String sectionName,
                                   @PathVariable("className") String className,
                                    Model model) {
        // fetch subject based on teacher id
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Retrieve username directly
            logger.info("Authenticated username: {}", username);

            StaffModel staffModel = staffRepository.findByUserName(username);
            if (staffModel != null) {
                logger.info("Found UserModel: {}", staffModel);

                // Filter classes to find all matching staffClasses
                for (Long subjectId : staffModel.getSubjectModelIds()) {
                    SubjectDto subjectDto = subjectService.getSubjectById(subjectId);
                    subjectDtoList.add(subjectDto);
                }
            } else {
                logger.warn("UserModel not found for username: {}", username);
            }
        } else {
            logger.warn("Authentication is null or not authenticated");
        }


        model.addAttribute("result", new ResultModel());

        List<SectionModel> filteredSections = sectionRepository.findAll()
                .stream()
                .filter(sec -> sec.getSectionName().equals(sectionName))
                .collect(Collectors.toList());

        List<SchoolClassModel> filteredClasses = schoolClassRepository.findAll()
                .stream()
                .filter(cls -> cls.getSectionId().equals(sectionName) && cls.getClassName().equals(className))
                .collect(Collectors.toList());

        List<StudentDto> filteredStudents = studentService.getAllStudents()
                .stream()
                .filter(cts -> cts.getSectionId().equals(sectionName) && cts.getStudentClassId().equals(className))
                .collect(Collectors.toList());

        model.addAttribute("students", filteredStudents);
        model.addAttribute("result", new ResultModel());
        model.addAttribute("sections", filteredSections);
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("studentClasses", filteredClasses);
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("subjects", subjectDtoList);

        model.addAttribute("sectionName", sectionName);
        model.addAttribute("className", className);

        return "results/new";
    }

    @PostMapping("/{sectionName}/{className}/create")
    public String saveResult(@Valid @ModelAttribute("result") ResultDto resultDto,
                             @PathVariable("sectionName") String sectionName,
                             @PathVariable("className") String className,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            // fetch subject based on teacher id
            List<SubjectDto> subjectDtoList = new ArrayList<>();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName(); // Retrieve username directly
                logger.info("Authenticated username: {}", username);

                StaffModel staffModel = staffRepository.findByUserName(username);
                if (staffModel != null) {
                    logger.info("Found UserModel: {}", staffModel);

                    // Filter classes to find all matching staffClasses
                    for (Long subjectId : staffModel.getSubjectModelIds()) {
                        SubjectDto subjectDto = subjectService.getSubjectById(subjectId);
                        subjectDtoList.add(subjectDto);
                    }
                } else {
                    logger.warn("UserModel not found for username: {}", username);
                }
            } else {
                logger.warn("Authentication is null or not authenticated");
            }


            model.addAttribute("result", new ResultModel());

            List<SectionModel> filteredSections = sectionRepository.findAll()
                    .stream()
                    .filter(sec -> sec.getSectionName().equals(sectionName))
                    .collect(Collectors.toList());

            List<SchoolClassModel> filteredClasses = schoolClassRepository.findAll()
                    .stream()
                    .filter(cls -> cls.getSectionId().equals(sectionName) && cls.getClassName().equals(className))
                    .collect(Collectors.toList());

            List<StudentDto> filteredStudents = studentService.getAllStudents()
                    .stream()
                    .filter(cts -> cts.getSectionId().equals(sectionName) && cts.getStudentClassId().equals(className))
                    .collect(Collectors.toList());

            model.addAttribute("students", filteredStudents);
            model.addAttribute("result", new ResultModel());
            model.addAttribute("sections", filteredSections);
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("studentClasses", filteredClasses);
            model.addAttribute("terms", termService.getAllTerms());
            model.addAttribute("subjects", subjectDtoList);

            model.addAttribute("sectionName", sectionName);
            model.addAttribute("className", className);

            return "results/new";
        }
        resultService.saveOrUpdateResult(resultDto);
        return "redirect:/results/" + sectionName + "/" + className;
    }

    @GetMapping("/{sectionName}/{className}/edit/{id}")
    public String updateResultForm(@PathVariable Long id,
                                   @PathVariable("sectionName") String sectionName,
                                   @PathVariable("className") String className,
                                   Model model) {

        // fetch subject based on teacher id
        List<SubjectDto> subjectDtoList = new ArrayList<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Retrieve username directly
            logger.info("Authenticated username: {}", username);

            StaffModel staffModel = staffRepository.findByUserName(username);
            if (staffModel != null) {
                logger.info("Found UserModel: {}", staffModel);

                // Filter classes to find all matching staffClasses
                for (Long subjectId : staffModel.getSubjectModelIds()) {
                    SubjectDto subjectDto = subjectService.getSubjectById(subjectId);
                    subjectDtoList.add(subjectDto);
                }
            } else {
                logger.warn("UserModel not found for username: {}", username);
            }
        } else {
            logger.warn("Authentication is null or not authenticated");
        }

        ResultDto result = resultService.getResultById(id);
        model.addAttribute("result", result);

        SectionDto sectionDto = SectionMapper.mapToDto(sectionRepository.findBySectionName(result.getSectionId()));
        SessionDto sessionDto = SessionMapper.mapToDto(sessionRepository.findBySessionName(result.getAcademicSessionId()));
        SchoolClassDto schoolClassDto = SchoolClassMapper.mapToDto(schoolClassRepository.findSchoolClassModelByClassNameAndSectionId(result.getStudentClassId(), result.getSectionId()));
        TermDto termDto = TermMapper.mapToDto(termRepository.findByTermName(result.getTermId()));
        SubjectDto subjectDto = SubjectMapper.mapToDto(subjectRepository.findBySubjectName(result.getSubjectId()));

        List<StudentDto> filteredStudents = studentService.getAllStudents()
                .stream()
                .filter(cts -> cts.getSectionId().equals(sectionName) && cts.getStudentClassId().equals(className))
                .collect(Collectors.toList());

        model.addAttribute("students", filteredStudents);
        model.addAttribute("sections", sectionDto);
        model.addAttribute("academicSessions", sessionDto);
        model.addAttribute("studentClasses", schoolClassDto);
        model.addAttribute("terms", termDto);
        model.addAttribute("subjects", subjectDtoList);

        // Default values for this result
        model.addAttribute("subjectDefault", subjectDto.getSubjectName());

        model.addAttribute("sectionName", sectionName);
        model.addAttribute("className", className);

        return "results/edit";
    }

    @PostMapping("/{sectionName}/{className}/update/{id}")
    public String updateResult(@PathVariable Long id,
                                @Valid @ModelAttribute("result") ResultDto resultDto,
                               @PathVariable("sectionName") String sectionName,
                               @PathVariable("className") String className,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            // fetch subject based on teacher id
            List<SubjectDto> subjectDtoList = new ArrayList<>();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName(); // Retrieve username directly
                logger.info("Authenticated username: {}", username);

                StaffModel staffModel = staffRepository.findByUserName(username);
                if (staffModel != null) {
                    logger.info("Found UserModel: {}", staffModel);

                    // Filter classes to find all matching staffClasses
                    for (Long subjectId : staffModel.getSubjectModelIds()) {
                        SubjectDto subjectDto = subjectService.getSubjectById(subjectId);
                        subjectDtoList.add(subjectDto);
                    }
                } else {
                    logger.warn("UserModel not found for username: {}", username);
                }
            } else {
                logger.warn("Authentication is null or not authenticated");
            }

            ResultDto resultData = resultService.getResultById(id);

            SectionDto sectionDto = SectionMapper.mapToDto(sectionRepository.findBySectionName(resultData.getSectionId()));
            SessionDto sessionDto = SessionMapper.mapToDto(sessionRepository.findBySessionName(resultData.getAcademicSessionId()));
            SchoolClassDto schoolClassDto = SchoolClassMapper.mapToDto(schoolClassRepository.findSchoolClassModelByClassNameAndSectionId(resultData.getStudentClassId(), resultData.getSectionId()));
            TermDto termDto = TermMapper.mapToDto(termRepository.findByTermName(resultData.getTermId()));
            SubjectDto subjectDto = SubjectMapper.mapToDto(subjectRepository.findBySubjectName(resultData.getSubjectId()));

            List<StudentDto> filteredStudents = studentService.getAllStudents()
                    .stream()
                    .filter(cts -> cts.getSectionId().equals(sectionName) && cts.getStudentClassId().equals(className))
                    .collect(Collectors.toList());

            model.addAttribute("students", filteredStudents);
            model.addAttribute("sections", sectionDto);
            model.addAttribute("academicSessions", sessionDto);
            model.addAttribute("studentClasses", schoolClassDto);
            model.addAttribute("terms", termDto);
            model.addAttribute("subjects", subjectDtoList);

            // Default values for this result
            model.addAttribute("subjectDefault", subjectDto.getSubjectName());

            model.addAttribute("sectionName", sectionName);
            model.addAttribute("className", className);

            return "results/edit";
        }
        resultDto.setId(id);
        resultService.saveOrUpdateResult(resultDto);
        return "redirect:/results/" + sectionName + "/" + className;
    }

    @GetMapping("/{sectionName}/{className}/delete/{id}")
    public String deleteResult(@PathVariable Long id,
                               @PathVariable("sectionName") String sectionName,
                               @PathVariable("className") String className) {
        resultService.deleteResult(id);
        return "redirect:/results/" + sectionName + "/" + className;
    }
}
