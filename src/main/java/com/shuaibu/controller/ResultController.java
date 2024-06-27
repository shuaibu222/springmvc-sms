package com.shuaibu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;
import com.shuaibu.service.ResultService;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.SessionService;
import com.shuaibu.service.SubjectService;
import com.shuaibu.service.TermService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/results")
@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
public class ResultController {
    
    private ResultService resultService;

    private SectionService sectionService;

    private SessionService sessionService;

    private SchoolClassService schoolClassService;

    private TermService termService;

    private SubjectService subjectService;

    public ResultController(ResultService resultService, SectionService sectionService, SessionService sessionService,
            SchoolClassService schoolClassService, TermService termService, SubjectService subjectService) {
        this.resultService = resultService;
        this.sectionService = sectionService;
        this.sessionService = sessionService;
        this.schoolClassService = schoolClassService;
        this.termService = termService;
        this.subjectService = subjectService;
    }

    @GetMapping
    public String listResults(Model model) {
        model.addAttribute("results", resultService.getAllResults());
        model.addAttribute("result", new ResultModel());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("studentClasses", schoolClassService.getAllSchoolClass());
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "results/list";
    }

    @GetMapping("/new")
    public String createResultForm(Model model) {
        model.addAttribute("result", new ResultModel());

        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("studentClasses", schoolClassService.getAllSchoolClass());
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "results/new";
    }

    @PostMapping("/create")
    public String saveResult(@Valid @ModelAttribute("result") ResultDto resultDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("result", resultDto);
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("studentClasses", schoolClassService.getAllSchoolClass());
            model.addAttribute("terms", termService.getAllTerms());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "results/new";
        }
        resultService.saveOrUpdateResult(resultDto);
        return "redirect:/results";
    }

    @GetMapping("/edit/{id}")
    public String updateResultForm(@PathVariable Long id, Model model) {
        ResultDto result = resultService.getResultById(id);
        model.addAttribute("result", result);

        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("studentClasses", schoolClassService.getAllSchoolClass());
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "results/edit";
    }

    @PostMapping("/update/{id}")
    public String updateResult(@PathVariable Long id,
                                @Valid @ModelAttribute("result") ResultDto resultDto, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", resultDto);
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("studentClasses", schoolClassService.getAllSchoolClass());
            model.addAttribute("terms", termService.getAllTerms());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "results/edit";
        }
        resultDto.setId(id);
        resultService.saveOrUpdateResult(resultDto);
        return "redirect:/results";
    }

    @GetMapping("/delete/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "redirect:/results";
    }
}
