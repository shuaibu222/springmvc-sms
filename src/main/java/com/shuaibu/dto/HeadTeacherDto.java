package com.shuaibu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeadTeacherDto {
    private Long id;

    private String teacherName;

    @NotEmpty(message = "* Teacher's name must be provided")
    private String teacherId;

    @NotEmpty(message = "* Section name must be provided")
    private String sectionId;

    private String sectionName;

}
