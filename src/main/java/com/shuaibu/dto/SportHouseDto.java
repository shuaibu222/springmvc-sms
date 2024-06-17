package com.shuaibu.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportHouseDto {
    private Long id;
    
    // Todo: Add validation
    private String sportHouseName;
    private List<Long> houseStudentIds;

}
