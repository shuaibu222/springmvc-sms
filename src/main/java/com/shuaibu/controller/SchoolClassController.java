package com.shuaibu.controller;


import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.StaffService;
import com.shuaibu.service.SubjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.service.SchoolClassService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/classes")
@PreAuthorize("hasRole('ADMIN')")
public class SchoolClassController {
    
    private final SchoolClassService schoolClassService;
    private final SubjectService subjectService;
    private final StaffService staffService;
    private final SectionService sectionService;
    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassController(SchoolClassService schoolClassService, SubjectService subjectService, StaffService staffService, SectionService sectionService, SectionRepository sectionRepository, SchoolClassRepository schoolClassRepository) {
        this.schoolClassService = schoolClassService;
        this.subjectService = subjectService;
        this.staffService = staffService;
        this.sectionService = sectionService;
        this.schoolClassRepository = schoolClassRepository;
    }

    @GetMapping
    public String listSchoolClass(Model model) {
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", new SchoolClassModel());
        model.addAttribute("schoolClasses", schoolClassService.getAllSchoolClass());
        return "schoolClasses/list";
    }

    @GetMapping("/new")
    public String createSchoolClassForm(Model model) {
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", new SchoolClassModel());
        return "schoolClasses/new";
    }

    @PostMapping("/create")
    public String saveSchoolClass(@Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("schoolClass", schoolClass);
            return "schoolClasses/new";
        }

        schoolClassService.saveOrUpdateSchoolClass(schoolClass);


        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String updateSchoolClassForm(@PathVariable Long id, Model model) {
        SchoolClassDto schoolClass = schoolClassService.getSchoolClassById(id);

        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", schoolClass);
        return "schoolClasses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSchoolClass(@PathVariable Long id,
                                @Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staffs", staffService.getAllStaffs());
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("schoolClass", schoolClass);
            return "schoolClasses/edit";
        }

        schoolClass.setId(id);
        // for re-updating the link btw teacher and class
        SchoolClassModel schoolClassModel = schoolClassRepository.findById(id).get();
        schoolClass.setStaffModels(schoolClassModel.getStaffModels());
        schoolClassService.saveOrUpdateSchoolClass(schoolClass);

        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchoolClass(@PathVariable Long id) {
        schoolClassService.deleteSchoolClass(id);
        return "redirect:/classes";
    }
}
