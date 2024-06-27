package com.shuaibu.repository;

import com.shuaibu.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    @Query("SELECT MAX(CAST(SUBSTRING(s.regNo, LENGTH(s.regNo) - 2, 3) AS int)) FROM StudentModel s WHERE s.sectionId = :sectionId")
    Integer findMaxRegNumberBySectionId(@Param("sectionId") Long sectionId);
}


