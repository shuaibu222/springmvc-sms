package com.shuaibu.repository;

import com.shuaibu.dto.ResultDto;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.ResultModel;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, Long> {
    List<ResultModel> findResultModelsBySectionIdAndStudentClassId(String section, String classId);

    ResultModel findByRegNo(@NotEmpty(message = "* Reg no. is mandatory") String regNo);

    List<ResultModel> findResultModelsByStudentClassId(String classId);
}

