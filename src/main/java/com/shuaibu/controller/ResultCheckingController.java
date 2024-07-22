package com.shuaibu.controller;

import com.shuaibu.dto.ResultDto;
import com.shuaibu.repository.ResultCheckingRepository;
import com.shuaibu.service.impl.ResultCheckingImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ResultCheckingDto;
import com.shuaibu.model.ResultCheckingModel;
import com.shuaibu.service.ResultCheckingService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/resultCheckings")
@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
public class ResultCheckingController {

    private ResultDto printedResult;
    private static final Logger logger = LoggerFactory.getLogger(ResultCheckingController.class);
    private final ResultCheckingService resultCheckingService;
    private final ResultCheckingRepository resultCheckingRepository;

    public ResultCheckingController(ResultCheckingService resultCheckingService, ResultCheckingRepository resultCheckingRepository) {
        this.resultCheckingService = resultCheckingService;
        this.resultCheckingRepository = resultCheckingRepository;
    }

    @GetMapping
    public String resultCheckingsForm(Model model) {
        model.addAttribute("resultChecking", new ResultCheckingModel());
        return "resultCheckings/resultForm";
    }

    @PostMapping
    public String printedResultChecking(@Valid @ModelAttribute("resultChecking") ResultCheckingDto resultChecking, BindingResult result, Model model) {
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
