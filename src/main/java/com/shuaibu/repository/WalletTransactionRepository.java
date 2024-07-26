package com.shuaibu.repository;

import com.shuaibu.model.WalletTransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransactionModel, Long> {
    List<WalletTransactionModel> findByWalletIdAndTermName(Long walletId, String termName);

    List<WalletTransactionModel> findByTermName(String termName);
}
