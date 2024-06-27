package com.shuaibu.controller;

import com.shuaibu.dto.SectionDto;
import com.shuaibu.service.SectionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final SectionService sectionService;

    public GlobalControllerAdvice(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @ModelAttribute
    public void populateSections(Model model) {
        List<SectionDto> sections = sectionService.getAllSections();
        model.addAttribute("sections", sections);
    }
}
