package com.shuaibu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;
import com.shuaibu.service.SectionService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/sections")
@PreAuthorize("hasRole('ADMIN')")
public class SectionController {
    
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public String showSectionsPage(Model model) {
        model.addAttribute("section", new SectionModel());
        model.addAttribute("sections", sectionService.getAllSections());
        return "sections/list";
    }


    @PostMapping
    public String listSections(@Valid @ModelAttribute("section") SectionDto section, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sections", sectionService.getAllSections());
            return "sections/list";
        }

        sectionService.saveOrUpdateSection(section);
        return "redirect:/sections";
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

        sectionService.saveOrUpdateSection(section);
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

//        // TODO
//        // Update associated school classes (optional, see note below)
//        if (existingSection.getClassIds() != null && !existingSection.getClassIds().isEmpty()) {
//                SchoolClassModel classModel = schoolClassRepository.findById(Long.valueOf(existingSection.getId())).get();
//                classModel.setSectionId(existingSection.getSectionName()); // Update reference to updated section
//                schoolClassRepository.save(classModel);
//        }

        sectionService.saveOrUpdateSection(section);
        return "redirect:/sections";
    }

    @GetMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "redirect:/sections";
    }
}
