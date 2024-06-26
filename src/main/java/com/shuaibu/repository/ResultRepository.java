package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ResultModel;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, Long> {
}

