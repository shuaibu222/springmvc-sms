package com.shuaibu.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.GradeModel;

@Repository
public interface GradeRepository extends JpaRepository<GradeModel, UUID> {
}

