package com.shuaibu.controller;

import com.shuaibu.dto.HeadTeacherDto;
import com.shuaibu.model.HeadTeacherModel;
import com.shuaibu.service.HeadTeacherService;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/headTeachers")
@PreAuthorize("hasRole('ADMIN')")
public class HeadTeacherController {

    private final HeadTeacherService headTeacherService;
    private final StaffService staffService;
    private final SectionService sectionService;

    public HeadTeacherController(HeadTeacherService headTeacherService, StaffService staffService, SectionService sectionService) {
        this.headTeacherService = headTeacherService;
        this.staffService = staffService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public String headTeacherForm(Model model) {
        model.addAttribute("headTeacherDto", new HeadTeacherModel());
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("headTeachers", headTeacherService.getAllHeadTeachers());
        return "headTeachers/list";
    }

    @PostMapping
    public String saveHeadTeacher(@Valid @ModelAttribute("headTeacherDto") HeadTeacherDto headTeacherDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("headTeachers", headTeacherService.getAllHeadTeachers());
            return "headTeachers/list";
        }

        headTeacherService.saveOrUpdateHeadTeacher(headTeacherDto);
        return "redirect:/headTeachers";
    }

    @GetMapping("/edit/{id}")
    public String updateHeadTeacherForm(@PathVariable Long id, Model model) {
        HeadTeacherDto headTeacher = headTeacherService.getHeadTeacherById(id);
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("headTeacherDto", headTeacher);
        return "headTeachers/edit";
    }

    @PostMapping("/update/{id}")
    public String updateHeadTeacher(@PathVariable Long id,
                                @Valid @ModelAttribute("headTeacherDto") HeadTeacherDto headTeacher,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("sections", sectionService.getAllSections());
            return "headTeachers/edit";
        }
        headTeacher.setId(id);
        headTeacherService.saveOrUpdateHeadTeacher(headTeacher);
        return "redirect:/headTeachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteHeadTeacher(@PathVariable Long id) {
        headTeacherService.deleteHeadTeacher(id);
        return "redirect:/headTeachers";
    }
}
