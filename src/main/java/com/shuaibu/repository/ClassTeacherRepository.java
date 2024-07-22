package com.shuaibu.repository;

import com.shuaibu.model.ClassTeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTeacherRepository extends JpaRepository<ClassTeacherModel, Long> {
}

