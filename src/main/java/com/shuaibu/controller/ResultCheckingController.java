package com.shuaibu.controller;

import com.shuaibu.dto.ResultDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ResultCheckingDto;
import com.shuaibu.model.ReportSheetModel;
import com.shuaibu.model.ResultCheckingModel;
import com.shuaibu.service.ResultCheckingService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/resultCheckings")
@PreAuthorize("hasRole('STUDENT')")
public class ResultCheckingController {

    private ReportSheetModel printedResult;
    private final ResultCheckingService resultCheckingService;

    public ResultCheckingController(ResultCheckingService resultCheckingService) {
        this.resultCheckingService = resultCheckingService;
    }

    @GetMapping
    public String resultCheckingsForm(Model model) {
        model.addAttribute("resultChecking", new ResultCheckingModel());
        return "resultCheckings/resultForm";
    }

    @PostMapping
    public String printedResultChecking(@Valid @ModelAttribute ResultCheckingDto resultChecking, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "resultCheckings/resultForm";
        }
        printedResult = resultCheckingService.searchResult(resultChecking);

        return "redirect:/resultCheckings/printedResult";
    }

    @GetMapping("/printedResult")
    public String printedResult(Model model) {
        model.addAttribute("printedResult", printedResult);
        return "resultCheckings/printedResult";
    }
}
