package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.TeacherModel;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherModel, Long> {
}

