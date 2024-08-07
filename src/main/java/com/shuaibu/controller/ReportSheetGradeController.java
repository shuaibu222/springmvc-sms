package com.shuaibu.controller;

import com.shuaibu.dto.ReportSheetGradeDto;
import com.shuaibu.model.ReportSheetGradeModel;
import com.shuaibu.service.ReportSheetGradeService;
import com.shuaibu.service.SectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/reportSheetGrades")
@PreAuthorize("hasRole('ADMIN')")
public class ReportSheetGradeController {

    private final SectionService sectionService;
    private final ReportSheetGradeService reportSheetGradeService;

    public ReportSheetGradeController(SectionService sectionService, ReportSheetGradeService reportSheetGradeService) {
        this.sectionService = sectionService;
        this.reportSheetGradeService = reportSheetGradeService;
    }

    @GetMapping
    public String showListReportSheetGrades(Model model) {
        model.addAttribute("reportSheetGrade", new ReportSheetGradeModel());
        model.addAttribute("sections", sectionService.getAllSections());
        model.addAttribute("reportSheetGrades", reportSheetGradeService.getAllReportSheetGrades());
        return "reportSheetGrades/list";
    }

    @PostMapping
    public String listReportSheetGradesSave(@Valid @ModelAttribute ReportSheetGradeDto reportSheetGrade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sections", sectionService.getAllSections());
            model.addAttribute("reportSheetGrades", reportSheetGradeService.getAllReportSheetGrades());
            return "reportSheetGrades/list";
        }
        reportSheetGradeService.saveReportSheetGrade(reportSheetGrade);
        return "redirect:/reportSheetGrades?success";
    }

    @GetMapping("/new")
    public String createReportSheetGradeForm(Model model) {
        model.addAttribute("reportSheetGrade", new ReportSheetGradeModel());
        model.addAttribute("sections", sectionService.getAllSections());
        return "reportSheetGrades/new";
    }

    @PostMapping("/create")
    public String saveReportSheetGrade(@Valid @ModelAttribute ReportSheetGradeDto reportSheetGrade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reportSheetGrade", reportSheetGrade);
            model.addAttribute("sections", sectionService.getAllSections());
            return "reportSheetGrades/new";
        }
        reportSheetGradeService.saveReportSheetGrade(reportSheetGrade);
        return "redirect:/reportSheetGrades";
    }

    @GetMapping("/edit/{id}")
    public String updateReportSheetGradeForm(@PathVariable Long id, Model model) {
        ReportSheetGradeDto reportSheetGrade = reportSheetGradeService.getReportSheetGradeById(id);
        model.addAttribute("reportSheetGrade", reportSheetGrade);
        model.addAttribute("sections", sectionService.getAllSections());
        return "reportSheetGrades/edit";
    }

    @PostMapping("/update/{id}")
    public String updateReportSheetGrade(@PathVariable Long id, @Valid @ModelAttribute ReportSheetGradeDto reportSheetGrade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reportSheetGrade", reportSheetGrade);
            model.addAttribute("sections", sectionService.getAllSections());
            return "reportSheetGrades/edit";
        }
        reportSheetGrade.setId(id);
        reportSheetGradeService.updateReportSheetGrade(reportSheetGrade);
        return "redirect:/reportSheetGrades";
    }

    @GetMapping("/delete/{id}")
    public String deleteReportSheetGrade(@PathVariable Long id) {
        reportSheetGradeService.deleteReportSheetGrade(id);
        return "redirect:/reportSheetGrades";
    }
}
