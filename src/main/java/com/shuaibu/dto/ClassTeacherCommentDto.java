package com.shuaibu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassTeacherCommentDto {
    private Long id;

    @NotEmpty(message = "* Teacher's name must be provided")
    private String teacherId;

    @NotEmpty(message = "* Class name must be provided")
    private String classId;

    private String teacherName;
    private String className;
    
    @NotNull(message = "* Range from is mandatory")
    @Min(value = 100, message = "* Cannot be less than 100")
    private Long rangeFrom;

    @NotNull(message = "* Range from is mandatory")
    @Min(value = 100, message = "* Cannot be less than 100")
    private Long rangeTo;

    @NotEmpty(message = "* Remark cannot be empty")
    private String remark;

}
