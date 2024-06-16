package com.shuaibu.mapper;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;

public class SportHouseMapper {
    
    public static SportHouseDto mapToDto(SportHouseModel sportHouseModel){
        SportHouseDto sportHouseDto = SportHouseDto.builder()
        .id(sportHouseModel.getId())
        .sportHouseName(sportHouseModel.getSportHouseName())
        .build();

        return sportHouseDto;
    }

    public static SportHouseModel mapToModel(SportHouseDto sportHouseDto){
        SportHouseModel sportHouseModel =SportHouseModel.builder()
        .id(sportHouseDto.getId())
        .sportHouseName(sportHouseDto.getSportHouseName())
        .build();

        return sportHouseModel;
    }
}
