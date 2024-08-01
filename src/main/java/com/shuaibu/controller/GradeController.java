package com.shuaibu.controller;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;
import com.shuaibu.service.GradeService;
import com.shuaibu.service.SectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/grades")
@PreAuthorize("hasRole('ADMIN')")
public class GradeController {

    private final SectionService sectionService;
    private final GradeService gradeService;

    public GradeController(SectionService sectionService, GradeService gradeService) {
        this.sectionService = sectionService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public String showListGrades(Model model) {
        model.addAttribute("grade", new GradeModel());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("grades", gradeService.getAllGrades());
        return "grades/list";
    }

    @PostMapping
    public String listGradesSave(@Valid @ModelAttribute("grade") GradeDto grade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("grades", gradeService.getAllGrades());
            return "grades/list";
        }
        gradeService.saveGrade(grade);
        return "redirect:/grades?success";
    }

    @GetMapping("/new")
    public String createGradeForm(Model model) {
        model.addAttribute("grade", new GradeModel());
        model.addAttribute("sections", sectionService.getAllSections());
        return "grades/new";
    }

    @PostMapping("/create")
    public String saveGrade(@Valid @ModelAttribute("grade") GradeDto grade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grade", grade);
            model.addAttribute("sections", sectionService.getAllSections());
            return "grades/new";
        }
        gradeService.saveGrade(grade);
        return "redirect:/grades";
    }

    @GetMapping("/edit/{id}")
    public String updateGradeForm(@PathVariable Long id, Model model) {
        GradeDto grade = gradeService.getGradeById(id);
        model.addAttribute("grade", grade);
        model.addAttribute("sections", sectionService.getAllSections());
        return "grades/edit";
    }

    @PostMapping("/update/{id}")
    public String updateGrade(@PathVariable Long id, @Valid @ModelAttribute("grade") GradeDto grade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grade", grade);
            model.addAttribute("sections", sectionService.getAllSections());
            return "grades/edit";
        }
        grade.setId(id);
        gradeService.updateGrade(grade);
        return "redirect:/grades";
    }

    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return "redirect:/grades";
    }
}
