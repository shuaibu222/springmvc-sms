package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.model.ResultModel;
import com.shuaibu.service.ResultService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/results")
public class ResultController {
    
    private ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public String listResults(Model model) {
        model.addAttribute("results", resultService.getAllResults());
        return "results/list";
    }

    @GetMapping("/new")
    public String createResultForm(Model model) {
        model.addAttribute("result", new ResultModel());
        return "results/new";
    }

    @PostMapping("/create")
    public String saveResult(@Valid @ModelAttribute("result") ResultDto resultDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("result", resultDto);
            return "results/new";
        }
        resultService.saveResult(resultDto);
        return "redirect:/results";
    }

    @GetMapping("/edit/{id}")
    public String updateResultForm(@PathVariable Long id, Model model) {
        ResultDto result = resultService.getResultById(id);
        model.addAttribute("result", result);
        return "results/edit";
    }

    @PostMapping("/update/{id}")
    public String updateResult(@PathVariable Long id,
                                @Valid @ModelAttribute("result") ResultDto resultDto, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", resultDto);
            return "results/edit";
        }
        resultDto.setId(id);
        resultService.updateResult(resultDto);
        return "redirect:/results";
    }

    @GetMapping("/delete/{id}")
    public String deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return "redirect:/results";
    }
}
