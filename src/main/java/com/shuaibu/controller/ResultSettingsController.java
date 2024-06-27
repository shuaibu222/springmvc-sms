package com.shuaibu.controller;


import com.shuaibu.service.SectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ResultSettingsDto;
import com.shuaibu.model.ResultSettingsModel;
import com.shuaibu.service.ResultSettingsService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/resultSettings")
@PreAuthorize("hasRole('ADMIN')")
public class ResultSettingsController {
    
    private ResultSettingsService resultSettingsService;
    private SectionService sectionService;

    public ResultSettingsController(ResultSettingsService resultSettingsService, SectionService sectionService) {
        this.resultSettingsService = resultSettingsService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public String listResultSettings(Model model) {
        model.addAttribute("resultSetting", new ResultSettingsModel());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("resultSettings", resultSettingsService.getAllResultSettings());
        return "resultSettings/list";
    }

    @GetMapping("/new")
    public String createResultSettingForm(Model model) {
        model.addAttribute("resultSetting", new ResultSettingsModel());
        return "resultSettings/new";
    }

    @PostMapping("/create")
    public String saveResultSetting(@Valid @ModelAttribute("result") ResultSettingsDto resultSettingDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("resultSetting", resultSettingDto);
            return "resultSettings/new";
        }
        resultSettingsService.saveOrUpdateResultSetting(resultSettingDto);
        return "redirect:/resultSettings";
    }

    @GetMapping("/edit/{id}")
    public String updateResultSettingForm(@PathVariable Long id, Model model) {
        ResultSettingsDto resultSetting = resultSettingsService.getResultSettingById(id);
        model.addAttribute("resultSetting", resultSetting);
        return "resultSettings/edit";
    }

    @PostMapping("/update/{id}")
    public String updateResultSetting(@PathVariable Long id,
                                @Valid @ModelAttribute("resultSetting") ResultSettingsDto resultSettingDto, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("resultSetting", resultSettingDto);
            return "resultSettings/edit";
        }
        resultSettingDto.setId(id);
        resultSettingsService.saveOrUpdateResultSetting(resultSettingDto);
        return "redirect:/resultSettings";
    }

    @GetMapping("/delete/{id}")
    public String deleteResultSetting(@PathVariable Long id) {
        resultSettingsService.deleteResultSetting(id);
        return "redirect:/resultSettings";
    }
}
