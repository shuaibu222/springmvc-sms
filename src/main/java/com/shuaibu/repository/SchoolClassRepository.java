package com.shuaibu.repository;

import com.shuaibu.dto.SchoolClassDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.SchoolClassModel;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClassModel, Long> {
    SchoolClassModel findByClassName(String classId);

    SchoolClassModel findSchoolClassModelByClassNameAndSectionId(String studentClassName, String sectionId);
}

