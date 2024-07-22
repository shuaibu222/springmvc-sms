package com.shuaibu.controller;


import com.shuaibu.model.SectionModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.StaffService;
import com.shuaibu.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final SchoolClassService schoolClassService;
    private final SubjectService subjectService;
    private final SectionService sectionService;
    private final SchoolClassRepository schoolClassRepository;
    private final SectionRepository sectionRepository;

    public SchoolClassController(SchoolClassService schoolClassService, SubjectService subjectService, SectionService sectionService, SchoolClassRepository schoolClassRepository, SectionRepository sectionRepository) {
        this.schoolClassService = schoolClassService;
        this.subjectService = subjectService;
        this.sectionService = sectionService;
        this.schoolClassRepository = schoolClassRepository;
        this.sectionRepository = sectionRepository;
    }

    @GetMapping
    public String listSchoolClass(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", new SchoolClassModel());
        model.addAttribute("schoolClasses", schoolClassService.getAllSchoolClass());
        return "schoolClasses/list";
    }

    @PostMapping
    public String listClassFormSave(@Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Repopulate necessary model attributes for the form
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("schoolClasses", schoolClassService.getAllSchoolClass());
            return "schoolClasses/list"; // Return to the list page if there are errors
        }

        // Handle saving logic
        schoolClassService.saveOrUpdateSchoolClass(schoolClass);

        return "redirect:/classes"; // Redirect to list page after successful submission
    }


    @GetMapping("/new")
    public String createSchoolClassForm(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", new SchoolClassModel());

        return "schoolClasses/new";
    }

    @PostMapping("/create")
    public String saveSchoolClass(@Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, BindingResult result, Model model) {
        if (result.hasErrors()) {
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
        SectionModel sectionDto = sectionRepository.findBySectionName(schoolClass.getSectionId());
        logger.info(sectionDto.toString());

        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("defaultSection", sectionDto);

        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("schoolClass", schoolClass);
        return "schoolClasses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSchoolClass(@PathVariable Long id,
                                @Valid @ModelAttribute("schoolClass") SchoolClassDto schoolClass, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            SectionModel sectionDto = sectionRepository.findBySectionName(schoolClass.getSectionId());

            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("defaultSection", sectionDto);

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
