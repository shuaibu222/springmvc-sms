package com.shuaibu.repository;

import com.shuaibu.model.ExpectedTermFeesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedTermFeesRepository extends JpaRepository<ExpectedTermFeesModel, Long> {
    ExpectedTermFeesModel findBySessionNameAndTermName(String sessionName, String termName);
}
