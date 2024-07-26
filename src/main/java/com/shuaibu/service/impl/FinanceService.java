package com.shuaibu.service.impl;

import com.shuaibu.controller.FinanceAnalysisController;
import com.shuaibu.model.WalletModel;
import com.shuaibu.model.WalletTransactionModel;
import com.shuaibu.repository.WalletRepository;
import com.shuaibu.repository.WalletTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceService {

    private static final Logger logger = LoggerFactory.getLogger(FinanceService.class);
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public FinanceService(WalletRepository walletRepository, WalletTransactionRepository walletTransactionRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public double getTotalFeesCollected(String termName) {
        List<WalletTransactionModel> transactions = walletTransactionRepository.findByTermName(termName);
        return transactions.stream()
                .mapToDouble(WalletTransactionModel::getAmount)
                .sum();
    }

    public double getTotalRevenue(String termName) {
        double totalFeesCollected = getTotalFeesCollected(termName);
        double totalDebt = getTotalDebt(termName);
        double totalOverpaid = walletRepository.findAll().stream()
                .mapToDouble(WalletModel::getBalance)
                .filter(balance -> balance > 0)
                .sum();

        return totalFeesCollected - totalDebt - totalOverpaid; // Ensure overpayments are not added
    }


    public double getTotalDebt(String termName) {
        List<WalletModel> wallets = walletRepository.findAll();
        return wallets.stream()
                .mapToDouble(WalletModel::getBalance)
                .filter(balance -> balance < 0)
                .sum();
    }

    public long getTotalStudentsWhoPaid() {
        List<WalletModel> wallets = walletRepository.findAll();
        return wallets.stream()
                .filter(WalletModel::getHasPaid)
                .count();
    }



    public long getTotalStudentsWhoDidNotPay() {
        // Directly count students with negative balances
        List<WalletModel> wallets = walletRepository.findAll();
        return wallets.stream()
                .filter(wallet -> wallet.getBalance() < 0)
                .count();
    }

    // for displaying non-paid students record
    public List<WalletModel> getStudentsWhoDidNotPay() {
        // Directly count students with negative balances
        List<WalletModel> wallets = walletRepository.findAll();
        return wallets.stream()
                .filter(wallet -> wallet.getBalance() < 0)
                .collect(Collectors.toList());
    }


}
