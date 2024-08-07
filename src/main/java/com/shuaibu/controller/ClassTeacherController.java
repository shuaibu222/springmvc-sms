package com.shuaibu.controller;

import com.shuaibu.dto.ClassTeacherDto;
import com.shuaibu.model.ClassTeacherModel;
import com.shuaibu.service.ClassTeacherService;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/classTeachers")
@PreAuthorize("hasRole('ADMIN')")
public class ClassTeacherController {

    private final ClassTeacherService classTeacherService;
    private final StaffService staffService;
    private final SchoolClassService schoolClassService;

    public ClassTeacherController(ClassTeacherService classTeacherService, StaffService staffService, SchoolClassService schoolClassService) {
        this.classTeacherService = classTeacherService;
        this.staffService = staffService;
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public String classTeacherForm(Model model) {
        model.addAttribute("classTeacherDto", new ClassTeacherModel());
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("classes", schoolClassService.getAllSchoolClass());
        model.addAttribute("classTeachers", classTeacherService.getAllClassTeachers());
        return "classTeachers/list";
    }

    @PostMapping
    public String saveClassTeacher(@Valid @ModelAttribute ClassTeacherDto classTeacherDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("classes", schoolClassService.getAllSchoolClass());
            model.addAttribute("classTeachers", classTeacherService.getAllClassTeachers());
            return "classTeachers/list";
        }

        classTeacherService.saveOrUpdateClassTeacher(classTeacherDto);
        return "redirect:/classTeachers?success";
    }

    @GetMapping("/edit/{id}")
    public String updateClassTeacherForm(@PathVariable Long id, Model model) {
        ClassTeacherDto classTeacher = classTeacherService.getClassTeacherById(id);
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("classes", schoolClassService.getAllSchoolClass());
        model.addAttribute("classTeacherDto", classTeacher);
        return "classTeachers/edit";
    }

    @PostMapping("/update/{id}")
    public String updateClassTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("classTeacherDto") ClassTeacherDto classTeacher,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("classes", schoolClassService.getAllSchoolClass());
            return "classTeachers/edit";
        }
        classTeacher.setId(id);
        classTeacherService.saveOrUpdateClassTeacher(classTeacher);
        return "redirect:/classTeachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteClassTeacher(@PathVariable Long id) {
        classTeacherService.deleteClassTeacher(id);
        return "redirect:/classTeachers";
    }
}
