package com.shuaibu.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.service.SchoolClassService;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/classes")
public class SchoolClassController {
    
    private SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public String listSchoolClasss(Model model) {
        model.addAttribute("schoolClasses", schoolClassService.getAllSchoolClass());
        return "schoolClasses/list";
    }

    @GetMapping("/new")
    public String createSchoolClassForm(Model model) {
        model.addAttribute("schoolClass", new SchoolClassModel());
        return "schoolClasses/new";
    }

    @PostMapping("/create")
    public String saveSchoolClass(@Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("schoolClass", schoolClass);
            return "schoolClasses/new";
        }
        schoolClassService.saveSchoolClass(schoolClass);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String updateSchoolClassForm(@PathVariable UUID id, Model model) {
        SchoolClassDto schoolClass = schoolClassService.getSchoolClassById(id);
        model.addAttribute("schoolClass", schoolClass);
        return "schoolClasses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSchoolClass(@PathVariable UUID id,
                                @Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("schoolClass", schoolClass);
            return "schoolClasses/edit";
        }
        schoolClass.setId(id);
        schoolClassService.updateSchoolClass(schoolClass);
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchoolClass(@PathVariable UUID id) {
        schoolClassService.deleteSchoolClass(id);
        return "redirect:/classes";
    }
}
