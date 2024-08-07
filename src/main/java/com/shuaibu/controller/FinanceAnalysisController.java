package com.shuaibu.controller;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.TermModel;
import com.shuaibu.repository.ExpectedTermFeesRepository;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.SessionService;
import com.shuaibu.service.impl.FinanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE_OFFICER')")
@RequestMapping("/finances")
public class FinanceAnalysisController {

    private final FinanceService financeService;
    private final TermRepository termRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ExpectedTermFeesRepository expectedTermFeesRepository;
    private final SessionService sessionService;

    public FinanceAnalysisController(FinanceService financeService, TermRepository termRepository, SchoolClassRepository schoolClassRepository, ExpectedTermFeesRepository expectedTermFeesRepository, SessionService sessionService) {
        this.financeService = financeService;
        this.termRepository = termRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.expectedTermFeesRepository = expectedTermFeesRepository;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String financeAnalysis(Model model) {

        String activeSessionName = sessionService.getAllSessions().stream()
                .filter(s -> s.getIsActive().equals("True"))
                .findFirst()
                .map(SessionDto::getSessionName)
                .orElseThrow(() -> new IllegalStateException("No active session found"));

        List<TermModel> termModel = termRepository.findAll();
        String termName = termModel.stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active term found"))
                .getTermName();

        model.addAttribute("totalFeesCollected", financeService.getTotalFeesCollected(termName));
        model.addAttribute("totalDebts", financeService.getTotalDebt(termName));
        model.addAttribute("total", financeService.getTotalRevenue(termName));  // expectedTermFeesRepository.findBySessionNameAndTermName(activeSessionName, termName).getTotalExpectedTermFee()
        model.addAttribute("paidStudent", financeService.getTotalStudentsWhoPaid());
        model.addAttribute("notPaidStudent", financeService.getTotalStudentsWhoDidNotPay());
        model.addAttribute("records", financeService.getStudentsWhoDidNotPay());
        model.addAttribute("classes", schoolClassRepository.findAll());
        
        return "finances/financeAnalysis";
    }
}
