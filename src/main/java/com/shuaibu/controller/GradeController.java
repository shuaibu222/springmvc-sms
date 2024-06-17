package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.GradeDto;
import com.shuaibu.model.GradeModel;
import com.shuaibu.service.GradeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/grades")
public class GradeController {
    
    private GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public String listGrades(Model model) {
        model.addAttribute("grades", gradeService.getAllGrades());
        return "grades/list";
    }

    @GetMapping("/new")
    public String createGradeForm(Model model) {
        model.addAttribute("grade", new GradeModel());
        return "grades/new";
    }

    @PostMapping("/create")
    public String saveGrade(@Valid @ModelAttribute("grade") GradeDto grade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grade", grade);
            return "grades/new";
        }
        gradeService.saveGrade(grade);
        return "redirect:/grades";
    }

    @GetMapping("/edit/{id}")
    public String updateGradeForm(@PathVariable Long id, Model model) {
        GradeDto grade = gradeService.getGradeById(id);
        model.addAttribute("grade", grade);
        return "grades/edit";
    }

    @PostMapping("/update/{id}")
    public String updateGrade(@PathVariable Long id,
                                @Valid @ModelAttribute("grade") GradeDto grade, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grade", grade);
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
