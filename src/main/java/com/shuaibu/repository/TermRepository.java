package com.shuaibu.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.TermModel;

@Repository
public interface TermRepository extends JpaRepository<TermModel, UUID> {
}

