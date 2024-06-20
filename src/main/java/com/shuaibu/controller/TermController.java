package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.TermDto;
import com.shuaibu.model.TermModel;
import com.shuaibu.service.TermService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/terms")
public class TermController {
    
    private TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping
    public String listTerms(Model model) {
        model.addAttribute("term", new TermModel());
        model.addAttribute("terms", termService.getAllTerms());
        return "terms/list";
    }

    @GetMapping("/new")
    public String createTermForm(Model model) {
        model.addAttribute("term", new TermModel());
        return "terms/new";
    }

    @PostMapping("/create")
    public String saveTerm(@Valid @ModelAttribute("term") TermDto term, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("term", term);
            return "terms/new";
        }
        termService.saveTerm(term);
        return "redirect:/terms";
    }

    @GetMapping("/edit/{id}")
    public String updateTermForm(@PathVariable Long id, Model model) {
        TermDto term = termService.getTermById(id);
        model.addAttribute("term", term);
        return "terms/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTerm(@PathVariable Long id,
                                @Valid @ModelAttribute("term") TermDto term, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("term", term);
            return "terms/edit";
        }
        term.setId(id);
        termService.updateTerm(term);
        return "redirect:/terms";
    }

    @GetMapping("/delete/{id}")
    public String deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
        return "redirect:/terms";
    }
}
