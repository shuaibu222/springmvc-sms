package com.shuaibu.repository;

import com.shuaibu.model.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<WalletModel, Long> {
    List<WalletModel> findByStudentClass(String className);
    List<WalletModel> findByIdIn(List<Long> studentId);
}
