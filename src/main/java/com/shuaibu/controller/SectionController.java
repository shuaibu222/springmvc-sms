package com.shuaibu.controller;


import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.repository.SchoolClassRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;
import com.shuaibu.service.SectionService;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/sections")
@PreAuthorize("hasRole('ADMIN')")
public class SectionController {
    
    private final SectionService sectionService;
    private final SchoolClassRepository schoolClassRepository;

    public SectionController(SectionService sectionService, SchoolClassRepository schoolClassRepository) {
        this.sectionService = sectionService;
        this.schoolClassRepository = schoolClassRepository;
    }

    @GetMapping
    public String listSections(Model model) {
        model.addAttribute("section", new SectionModel());
        model.addAttribute("sections", sectionService.getAllSections());
        return "sections/list";
    }

    @GetMapping("/new")
    public String createSectionForm(Model model) {
        model.addAttribute("section", new SectionModel());
        return "sections/new";
    }

    @PostMapping("/create")
    public String saveSection(@Valid @ModelAttribute("section") SectionDto section, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("section", section);
            return "sections/new";
        }
        sectionService.saveSection(section);
        return "redirect:/sections";
    }

    @GetMapping("/edit/{id}")
    public String updateSectionForm(@PathVariable Long id, Model model) {
        SectionDto section = sectionService.getSectionById(id);
        model.addAttribute("section", section);
        return "sections/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSection(@PathVariable Long id,
                                @Valid @ModelAttribute("section") SectionDto section, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("section", section);
            return "sections/edit";
        }

        SectionDto existingSection = sectionService.getSectionById(id);
        section.setId(id); // Set the ID for update

        // Update section name
        existingSection.setSectionName(section.getSectionName());

        // TODO
        // Update associated school classes (optional, see note below)
        if (existingSection.getClassIds() != null && !existingSection.getClassIds().isEmpty()) {
            for (String schoolClass : existingSection.getClassIds()) {
                SchoolClassModel classModel = schoolClassRepository.findById(Long.valueOf(schoolClass)).get();
                classModel.setSectionId(existingSection.getSectionName()); // Update reference to updated section
            }
        }

        sectionService.updateSection(section);
        return "redirect:/sections";
    }

    @GetMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "redirect:/sections";
    }
}
