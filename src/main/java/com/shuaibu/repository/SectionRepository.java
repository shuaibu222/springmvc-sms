package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.SectionModel;

@Repository
public interface SectionRepository extends JpaRepository<SectionModel, Long> {
    SectionModel findBySectionName(String sectionId);
}

