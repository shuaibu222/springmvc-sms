package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;

public interface SportHouseService {
    List<SportHouseDto> getAllSportHouses();
    SportHouseDto getSportHouseById(UUID id);
    SportHouseModel saveSportHouse(SportHouseDto sportHouseDto);
    void updateSportHouse(SportHouseDto sportHouseDto);
    void deleteSportHouse(UUID id);
}
