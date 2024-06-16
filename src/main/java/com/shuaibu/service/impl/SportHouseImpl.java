package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;
import com.shuaibu.repository.SportHouseRepository;
import com.shuaibu.service.SportHouseService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SportHouseMapper.*;

@Service
public class SportHouseImpl implements SportHouseService {
    
    private SportHouseRepository sportHouseRepository;

    public SportHouseImpl(SportHouseRepository sportHouseRepository) {
        this.sportHouseRepository = sportHouseRepository;
    }

    @Override
    public List<SportHouseDto> getAllSportHouses() {
        List<SportHouseModel> sportHouses = sportHouseRepository.findAll();
        return sportHouses.stream().map(sportHouse -> mapToDto(sportHouse)).collect(Collectors.toList());
    }

    @Override
    public SportHouseDto getSportHouseById(UUID id) {
        return mapToDto(sportHouseRepository.findById(id).get());
    }

    @Override
    public SportHouseModel saveSportHouse(SportHouseDto sportHouseDto) {
        return sportHouseRepository.save(mapToModel(sportHouseDto));
    }

    @Override
    public void updateSportHouse(SportHouseDto sportHouseDto) {
        sportHouseRepository.save(mapToModel(sportHouseDto));
    }
    
    @Override
    public void deleteSportHouse(UUID id) {
        sportHouseRepository.deleteById(id);
    }
}
