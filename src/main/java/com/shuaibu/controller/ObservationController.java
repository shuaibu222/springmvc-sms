package com.shuaibu.controller;

import com.shuaibu.dto.*;
import com.shuaibu.model.ReportSheetModel;
import com.shuaibu.model.StudentModel;
import com.shuaibu.repository.ReportSheetRepository;
import com.shuaibu.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/observations")
@PreAuthorize("hasRole('STAFF')")
public class ObservationController {

    private final StudentService studentService;
    private final TermService termService;
    private final SessionService sessionService;
    private final ReportSheetRepository reportSheetRepository;

    public ObservationController(StudentService studentService, ReportSheetRepository reportSheetRepository, SessionService sessionService, TermService termService) {
        this.studentService = studentService;
        this.termService = termService;
        this.sessionService = sessionService;
        this.reportSheetRepository = reportSheetRepository;
    }

    @GetMapping("/{className}")
    public String listObservations(@PathVariable String className,
                                Model model) {

        List<StudentDto> filteredStudents = studentService.getAllStudents()
                .stream()
                .filter(cts -> cts.getStudentClassName().equals(className))
                .collect(Collectors.toList());

        List<ReportSheetModel> reportSheetModels = reportSheetRepository.findAll()
                        .stream()
                        .filter(r -> r.getStudentClass().equals(className))
                        .collect(Collectors.toList());


        model.addAttribute("students", filteredStudents);
        model.addAttribute("observations", Arrays.asList("Fluency", "Adjustment to Class", 
                        "Responsiveness", "Sense of Responsibility", "Relationsip with other Pupils", 
                        "Hand Writing", "Application to Work"));
        model.addAttribute("scale", Arrays.asList("A", "B", "C", "D", "E", "F"));
        model.addAttribute("className", className);
        model.addAttribute("reportsheets", reportSheetModels);

        return "observations/list";
    }

    @PostMapping("/{className}")
    public String saveOrUpdateObservation(@PathVariable String className, 
                                    @RequestParam String regNo,
                                    @RequestParam String observations,
                                    @RequestParam String scale,
                                    Model model) {

        String activeSessionName = sessionService.getAllSessions().stream()
                .filter(s -> s.getIsActive().equals("True"))
                .findFirst()
                .map(SessionDto::getSessionName)
                .orElseThrow(() -> new IllegalStateException("No active session found"));

        String activeTermName = termService.getAllTerms().stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .map(TermDto::getTermName)
                .orElseThrow(() -> new IllegalStateException("No active term found"));

        ReportSheetModel reportSheetModel = reportSheetRepository.findByRegNoAndTermAndAcademicSession(regNo, activeTermName, activeSessionName);
        if (reportSheetModel == null) {
            throw new IllegalArgumentException("No report sheet found");
        }

        switch (observations) {
            case "Fluency":
                reportSheetModel.setFluency(scale);
                break;
            case "Adjustment to Class":
                reportSheetModel.setAdjustmentToClass(scale);
                break;
            case "Responsiveness":
                reportSheetModel.setResponsiveness(scale);
                break;
            case "Sense of Responsibility":
                reportSheetModel.setSenseOfResponsibility(scale);
                break;
            case "Relationsip with other Pupils":
                reportSheetModel.setRelationshipsWithOtherPupils(scale);
                break;
            case "Hand Writing":
                reportSheetModel.setHandWriting(scale);
                break;
            case "Application to Work":
                reportSheetModel.setApplicationToWork(scale);
            default:
                break;
        }

        reportSheetRepository.save(reportSheetModel);


        return "redirect:/observations/" + className + "?success";
    }

}