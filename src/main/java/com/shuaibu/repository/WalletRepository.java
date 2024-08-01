package com.shuaibu.repository;

import com.shuaibu.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<WalletModel, Long> {
    List<WalletModel> findByStudentClassAndIsActive(String className, String isActive);

    void deleteByStudentId(Long id);

    WalletModel findByRegNo(String regNo);
}
