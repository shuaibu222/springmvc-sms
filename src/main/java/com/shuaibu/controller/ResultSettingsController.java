package com.shuaibu.controller;


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
public class ResultSettingsController {
    
    private ResultSettingsService resultSettingsService;

    public ResultSettingsController(ResultSettingsService resultSettingsService) {
        this.resultSettingsService = resultSettingsService;
    }

    @GetMapping
    public String listResultSettings(Model model) {
        model.addAttribute("resultSettings", resultSettingsService.getAllResultSettings());
        return "resultSettings/list";
    }

    @GetMapping("/new")
    public String createResultSettingForm(Model model) {
        model.addAttribute("result", new ResultSettingsModel());
        return "resultSettings/new";
    }

    @PostMapping("/create")
    public String saveResultSetting(@Valid @ModelAttribute("result") ResultSettingsDto resultSettingDto, BindingResult result, Model model) {
        if (result.hasErrors()){
            model.addAttribute("resultSetting", resultSettingDto);
            return "resultSettings/new";
        }
        resultSettingsService.saveResultSetting(resultSettingDto);
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
        resultSettingsService.updateResultSetting(resultSettingDto);
        return "redirect:/resultSettings";
    }

    @GetMapping("/delete/{id}")
    public String deleteResultSetting(@PathVariable Long id) {
        resultSettingsService.deleteResultSetting(id);
        return "redirect:/resultSettings";
    }
}
