package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.TeacherDto;
import com.shuaibu.model.TeacherModel;
import com.shuaibu.service.TeacherService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers/list";
    }

    @GetMapping("/new")
    public String createTeacherForm(Model model) {
        model.addAttribute("teacher", new TeacherModel());
        return "teachers/new";
    }

    @PostMapping("/create")
    public String saveTeacher(@Valid @ModelAttribute("teacher") TeacherDto teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teachers/new";
        }
        teacherService.saveTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        TeacherDto teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "teachers/edit";
    }

    @PostMapping("/{id}")
    public String updateTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("teacher") TeacherDto teacher, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "teachers/edit";
        }
        teacher.setId(id);
        teacherService.updateTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
