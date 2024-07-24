package com.shuaibu.controller;

import com.shuaibu.model.TermModel;
import com.shuaibu.model.WalletModel;
import com.shuaibu.model.WalletTransactionModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.repository.WalletRepository;
import com.shuaibu.repository.WalletTransactionRepository;
import com.shuaibu.service.SessionService;
import com.shuaibu.service.TermService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fees")
@PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE_OFFICER')")
public class WalletController {

    private final WalletRepository walletRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final TermRepository termRepository;
    private final TermService termService;
    private final SessionService sessionService;

    public WalletController(WalletRepository walletRepository,
                            SchoolClassRepository schoolClassRepository,
                            WalletTransactionRepository walletTransactionRepository, TermRepository termRepository, TermService termService, SessionService sessionService) {
        this.walletRepository = walletRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.termRepository = termRepository;
        this.termService = termService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String walletPage(Model model) {
        model.addAttribute("wallet", new WalletModel());
        model.addAttribute("classes", schoolClassRepository.findAll());
        model.addAttribute("wallets", walletRepository.findAll());
        return "wallets/list";
    }

    @GetMapping("/pay/{id}")
    public String payFees(Model model, @PathVariable Long id) {
        model.addAttribute("walletId", id);
        model.addAttribute("transaction", new WalletTransactionModel());
        model.addAttribute("terms", termService.getAllTerms());
        model.addAttribute("sessions", sessionService.getAllSessions());
        return "wallets/pay";
    }

    @PostMapping("/pay")
    public String processPayment(@ModelAttribute WalletTransactionModel walletTransactionModel) {
        WalletModel wallet = walletRepository.findById(walletTransactionModel.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid wallet ID"));

        // Ensure amount is valid
        if (walletTransactionModel.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // Create and save transaction
        WalletTransactionModel transaction = WalletTransactionModel.builder()
                .fromAccount(walletTransactionModel.getFromAccount())
                .bankName(walletTransactionModel.getBankName())
                .transactionDate(walletTransactionModel.getTransactionDate())
                .termName(walletTransactionModel.getTermName())
                .sessionName(walletTransactionModel.getSessionName())
                .amount(walletTransactionModel.getAmount())
                .walletId(walletTransactionModel.getWalletId())
                .build();

        walletTransactionRepository.save(transaction);

        // Update the wallet balance
        wallet.setBalance(wallet.getBalance() + walletTransactionModel.getAmount());
        wallet.getTransactionIds().add(transaction.getId());
        walletRepository.save(wallet);

        return "redirect:/fees";
    }


    @GetMapping("/walletsByClass")
    @ResponseBody
    public List<WalletModel> getWalletsByClass(@RequestParam String className) {
        return walletRepository.findByStudentClass(className);
    }

    @GetMapping("/transactions/{walletId}")
    public String viewTransactions(@PathVariable Long walletId, Model model) {
        WalletModel wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wallet ID"));

        List<TermModel> termModel = termRepository.findAll();
        String termName = termModel.stream()
                .filter(t -> t.getIsActive().equals("True"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active term found"))
                .getTermName();

        List<WalletTransactionModel> transactions = walletTransactionRepository.findByWalletIdAndTermName(walletId, termName);

        model.addAttribute("wallet", wallet);
        model.addAttribute("transactions", transactions);
        return "wallets/transactions";
    }

    @GetMapping("/edit/{id}")
    public String editBalance(@PathVariable Long id, Model model) {
        WalletModel wallet = walletRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wallet ID"));
        model.addAttribute("wallet", wallet);
        return "wallets/edit";
    }

    @PostMapping("/updateBalance")
    public String updateBalance(@ModelAttribute WalletModel walletModel) {
        WalletModel wallet = walletRepository.findById(walletModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid wallet ID"));

        // Update balance
        wallet.setBalance(walletModel.getBalance());
        walletRepository.save(wallet);

        return "redirect:/fees";
    }

    @PostMapping("/setTermDebt")
    public String setTermDebt(@RequestParam String className, @RequestParam Double full,
                              @RequestParam Double partial) {
        List<WalletModel> wallets = walletRepository.findByStudentClass(className);

        for (WalletModel wallet : wallets) {
            switch (wallet.getAdmissionType()) {
                case "Fully-funded":
                    // Add new debt to the existing balance
                    wallet.setBalance(wallet.getBalance() - full);  // Since balance is negative, adding debt decreases it further
                    break;
                case "Partial-scholarship":
                    // Add new debt to the existing balance
                    wallet.setBalance(wallet.getBalance() - partial);  // Same here, adding debt decreases the balance
                    break;
            }
            walletRepository.save(wallet);
        }
        return "redirect:/fees";
    }
}
