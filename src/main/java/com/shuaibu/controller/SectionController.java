package com.shuaibu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.model.SectionModel;
import com.shuaibu.service.SectionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sections")
public class SectionController {
    
    private SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
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
        section.setId(id);
        sectionService.updateSection(section);
        return "redirect:/sections";
    }

    @GetMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "redirect:/sections";
    }
}
