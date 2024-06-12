package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ResultSettingsModel;

@Repository
public interface ResultSettingsRepository extends JpaRepository<ResultSettingsModel, Long> {
}

