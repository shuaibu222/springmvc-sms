package com.shuaibu.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shuaibu.model.TermModel;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.StaffService;
import com.shuaibu.service.StudentService;
import com.shuaibu.service.impl.FinanceService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final StudentService studentService;
    private final StaffService staffService;
    private final SchoolClassService schoolClassService;
    private final FinanceService financeService;
    private final TermRepository termRepository;

    public DashboardController(StudentService studentService, StaffService staffService, SchoolClassService schoolClassService, FinanceService financeService, TermRepository termRepository) {
        this.studentService = studentService;
        this.staffService = staffService;
        this.schoolClassService = schoolClassService;
        this.financeService = financeService;
        this.termRepository = termRepository;
    }

    @GetMapping
    public String Dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<TermModel> termModel = termRepository.findAll();
        String termName = termModel.stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active term found"))
                .getTermName();
    
        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("students", studentService.getAllStudents().stream().count());
        model.addAttribute("staffs", staffService.getAllStaffs().stream().count());
        model.addAttribute("allClasses", schoolClassService.getAllSchoolClass().stream().count());
        model.addAttribute("totalFeesCollected", financeService.getTotalFeesCollected(termName));
        model.addAttribute("totalDebts", financeService.getTotalDebt(termName));
        model.addAttribute("notPaidStudents", financeService.getTotalStudentsWhoDidNotPay());
        return "dashboard/dashboard";
    }
}
