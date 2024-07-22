package com.shuaibu.repository;

import com.shuaibu.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletModel, Long> {
}
