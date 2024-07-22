package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ResultCheckingModel;

@Repository
public interface ResultCheckingRepository extends JpaRepository<ResultCheckingModel, Long> {
}

