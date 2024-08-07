package com.shuaibu.repository;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    StudentModel findByRegNoAndPassword(@NotEmpty(message = "* Reg no. is mandatory") String regNo, @NotEmpty(message = "* Password must be provided") String password);

    StudentModel findByUserName(String username);
    List<StudentDto> findByStudentClassIdAndIsActive(String classId, String isActive);

    List<StudentModel> findByStudentClassNameAndIsActive(String studentClassId, String string);

}


