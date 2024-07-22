package com.shuaibu.controller;

import com.shuaibu.repository.TermRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;
import com.shuaibu.service.TermService;

import jakarta.validation.Valid;

import java.util.Arrays;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/terms")
@PreAuthorize("hasRole('ADMIN')")
public class TermController {
    
    private final TermService termService;
    private final TermRepository termRepository;

    public TermController(TermService termService, TermRepository termRepository) {
        this.termService = termService;
        this.termRepository = termRepository;
    }

    @GetMapping
    public String listTerms(Model model) {
        model.addAttribute("term", new TermModel());
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        model.addAttribute("terms", termRepository.findAll());
        return "terms/list";
    }

    @PostMapping
    public String saveListTerm(@Valid @ModelAttribute("term") TermDto term,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("terms", termRepository.findAll());
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            return "terms/list";
        }
        termService.saveOrUpdateTerm(term);
        return "redirect:/terms";
    }

    @GetMapping("/new")
    public String createTermForm(Model model) {
        model.addAttribute("term", new TermModel());
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "terms/new";
    }

    @PostMapping("/create")
    public String saveTerm(@Valid @ModelAttribute("term") TermDto term, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("term", term);
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            return "terms/new";
        }
        termService.saveOrUpdateTerm(term);
        return "redirect:/terms";
    }

    @GetMapping("/edit/{id}")
    public String updateTermForm(@PathVariable Long id, Model model) {
        TermDto term = termService.getTermById(id);
        model.addAttribute("term", term);
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "terms/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTerm(@PathVariable Long id,
                                @Valid @ModelAttribute("term") TermDto term, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("term", term);
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            return "terms/edit";
        }
        term.setId(id);
        termService.saveOrUpdateTerm(term);
        return "redirect:/terms";
    }

    @GetMapping("/delete/{id}")
    public String deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
        return "redirect:/terms";
    }
}
