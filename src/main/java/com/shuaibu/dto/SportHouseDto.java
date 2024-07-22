package com.shuaibu.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportHouseDto {
    private Long id;

    @NotEmpty(message = "* Sport House Name is mandatory")
    private String sportHouseName;

    private Set<Long> houseStudentIds;

}
