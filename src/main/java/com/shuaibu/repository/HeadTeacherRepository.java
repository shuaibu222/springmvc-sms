package com.shuaibu.repository;

import com.shuaibu.model.HeadTeacherModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadTeacherRepository extends JpaRepository<HeadTeacherModel, Long> {
}

