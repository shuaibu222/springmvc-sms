package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;

public interface SportHouseService {
    List<SportHouseDto> getAllSportHouses();
    SportHouseDto getSportHouseById(Long id);
    SportHouseModel saveSportHouse(SportHouseDto sportHouseDto);
    void updateSportHouse(SportHouseDto sportHouseDto);
    void deleteSportHouse(Long id);
}
