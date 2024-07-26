package com.shuaibu.controller;

import com.shuaibu.model.TermModel;
import com.shuaibu.model.WalletModel;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.repository.WalletRepository;
import com.shuaibu.service.impl.FinanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FinanceAnalysisController.class);
    private final FinanceService financeService;
    private final TermRepository termRepository;

    public FinanceAnalysisController(FinanceService financeService, TermRepository termRepository) {
        this.financeService = financeService;
        this.termRepository = termRepository;
    }

    @GetMapping
    public String financeAnalysis(Model model) {

        List<TermModel> termModel = termRepository.findAll();
        String termName = termModel.stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active term found"))
                .getTermName();

        model.addAttribute("totalFeesCollected", financeService.getTotalFeesCollected(termName));
        model.addAttribute("totalDebts", financeService.getTotalDebt(termName));
        model.addAttribute("total", financeService.getTotalRevenue(termName));
        model.addAttribute("paidStudent", financeService.getTotalStudentsWhoPaid());
        model.addAttribute("notPaidStudent", financeService.getTotalStudentsWhoDidNotPay());
        model.addAttribute("records", financeService.getStudentsWhoDidNotPay());
        return "finances/financeAnalysis";
    }
}
