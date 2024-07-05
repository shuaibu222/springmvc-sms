package com.shuaibu.mapper;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;

public class SportHouseMapper {
    
    public static SportHouseDto mapToDto(SportHouseModel sportHouseModel){

        return SportHouseDto.builder()
        .id(sportHouseModel.getId())
        .sportHouseName(sportHouseModel.getSportHouseName())
        .build();
    }

    public static SportHouseModel mapToModel(SportHouseDto sportHouseDto){

        return SportHouseModel.builder()
        .id(sportHouseDto.getId())
        .sportHouseName(sportHouseDto.getSportHouseName())
        .build();
    }
}
