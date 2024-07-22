package com.shuaibu.repository;

import com.shuaibu.model.StudentIdCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentIdCounterRepository extends JpaRepository<StudentIdCounter, Long> {
    StudentIdCounter findByAcademicYear(int academicYear);
}
