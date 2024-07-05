package com.shuaibu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.service.SubjectService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/subjects")
@PreAuthorize("hasRole('ADMIN')")
public class SubjectController {
    
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subject", new SubjectModel());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects/list";
    }

    @GetMapping("/new")
    public String createSubjectForm(Model model) {
        model.addAttribute("subject", new SubjectModel());
        return "subjects/new";
    }

    @PostMapping("/create")
    public String saveSubject(@Valid @ModelAttribute("subject") SubjectDto subject, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("subject", subject);
            return "subjects/new";
        }
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String updateSubjectForm(@PathVariable Long id, Model model) {
        SubjectDto subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);
        return "subjects/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSubject(@PathVariable Long id,
                                @Valid @ModelAttribute("subject") SubjectDto subject, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("subject", subject);
            return "subjects/edit";
        }
        subject.setId(id);
        subjectService.updateSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }
}
