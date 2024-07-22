package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassTeacherDto {
    private Long id;

    private String teacherName;

    @NotEmpty(message = "* Teacher's name must be provided")
    private String teacherId;

    @NotEmpty(message = "* Class name must be provided")
    private String classId;

    private String className;

}
