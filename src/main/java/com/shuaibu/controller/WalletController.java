package com.shuaibu.controller;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.dto.TermDto;
import com.shuaibu.model.ExpectedTermFeesModel;
import com.shuaibu.model.TermModel;
import com.shuaibu.model.WalletModel;
import com.shuaibu.model.WalletTransactionModel;
import com.shuaibu.repository.*;
import com.shuaibu.service.SessionService;
import com.shuaibu.service.TermService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final ExpectedTermFeesRepository expectedTermFeesRepository;

    public WalletController(WalletRepository walletRepository,
                            SchoolClassRepository schoolClassRepository,
                            WalletTransactionRepository walletTransactionRepository, TermRepository termRepository, TermService termService, SessionService sessionService, ExpectedTermFeesRepository expectedTermFeesRepository) {
        this.walletRepository = walletRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.termRepository = termRepository;
        this.termService = termService;
        this.sessionService = sessionService;
        this.expectedTermFeesRepository = expectedTermFeesRepository;
    }

    @GetMapping
    public String walletPage(Model model) {
        List<WalletModel> wallets = walletRepository.findAll()
                        .stream()
                                .filter(w -> w.getIsActive().equals("True"))
                                        .collect(Collectors.toList());

        model.addAttribute("wallet", new WalletModel());
        model.addAttribute("classes", schoolClassRepository.findAll());
        model.addAttribute("wallets", wallets);
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
        // set hasPaid to true if condition met
        wallet.setHasPaid(wallet.getBalance() >= 0);


        wallet.getTransactionIds().add(transaction.getId());
        walletRepository.save(wallet);

        return "redirect:/fees";
    }


    @GetMapping("/walletsByClass")
    @ResponseBody
    public List<WalletModel> getWalletsByClass(@RequestParam String className) {
        return walletRepository.findByStudentClassAndIsActive(className, "True");
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

    @GetMapping("/transactions")
    public String searchTransactionsForm(Model model) {
        
        model.addAttribute("wallet", new WalletModel());

        return "wallets/allTransactions";
    }

    @PostMapping("/allTransactions")
    public String searchTransactions(@RequestParam String regNo, Model model) {
        WalletModel wallet = walletRepository.findByRegNo(regNo);

        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found.");
        }

        List<WalletTransactionModel> transactions = walletTransactionRepository.findByWalletId(wallet.getId());

        model.addAttribute("wallet", wallet);
        model.addAttribute("transactions", transactions);
        return "wallets/allTransactions";
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
                            @RequestParam Double partial, Model model) {
        // Retrieve active session and term names
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

        // Retrieve or initialize ExpectedTermFeesModel
        ExpectedTermFeesModel expectedTermFees = expectedTermFeesRepository
                .findBySessionNameAndTermName(activeSessionName, activeTermName);
        if (expectedTermFees == null) {
            expectedTermFees = new ExpectedTermFeesModel();
            expectedTermFees.setSessionName(activeSessionName);
            expectedTermFees.setTermName(activeTermName);
            expectedTermFees.setTotalExpectedTermFee(0.0);
        }

        // Set or update expected term fees
        List<WalletModel> wallets = walletRepository.findByStudentClassAndIsActive(className, "True");
        double additionalFees = 0.0;

        for (WalletModel wallet : wallets) {
            switch (wallet.getAdmissionType()) {
                case "Fully-funded":
                    additionalFees += full;
                    wallet.setBalance(wallet.getBalance() - full);
                    break;
                case "Partial-scholarship":
                    additionalFees += partial;
                    wallet.setBalance(wallet.getBalance() - partial);
                    break;
            }
            walletRepository.save(wallet);
        }

        // Update expected term fees
        expectedTermFees.setTotalExpectedTermFee(expectedTermFees.getTotalExpectedTermFee() + additionalFees);

        expectedTermFeesRepository.save(expectedTermFees);

        return "redirect:/fees";
    }

}
