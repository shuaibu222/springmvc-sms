package com.shuaibu.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportHouseDto {
    private UUID id;
    
    // Todo: Add validation
    private String sportHouseName;
    private List<UUID> houseStudentIds;

}
