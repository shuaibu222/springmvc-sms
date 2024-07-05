package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.SportHouseDto;

public interface SportHouseService {
    List<SportHouseDto> getAllSportHouses();
    SportHouseDto getSportHouseById(Long id);
    void saveSportHouse(SportHouseDto sportHouseDto);
    void updateSportHouse(SportHouseDto sportHouseDto);
    void deleteSportHouse(Long id);
}
