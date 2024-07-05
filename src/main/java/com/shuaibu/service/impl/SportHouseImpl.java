package com.shuaibu.service.impl;

import com.shuaibu.mapper.SportHouseMapper;
import org.springframework.stereotype.Service;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;
import com.shuaibu.repository.SportHouseRepository;
import com.shuaibu.service.SportHouseService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.SportHouseMapper.*;

@Service
public class SportHouseImpl implements SportHouseService {
    
    private final SportHouseRepository sportHouseRepository;

    public SportHouseImpl(SportHouseRepository sportHouseRepository) {
        this.sportHouseRepository = sportHouseRepository;
    }

    @Override
    public List<SportHouseDto> getAllSportHouses() {
        List<SportHouseModel> sportHouses = sportHouseRepository.findAll();
        return sportHouses.stream().map(SportHouseMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public SportHouseDto getSportHouseById(Long id) {
        return mapToDto(sportHouseRepository.findById(id).get());
    }

    @Override
    public void saveSportHouse(SportHouseDto sportHouseDto) {
        sportHouseRepository.save(mapToModel(sportHouseDto));
    }

    @Override
    public void updateSportHouse(SportHouseDto sportHouseDto) {
        sportHouseRepository.save(mapToModel(sportHouseDto));
    }
    
    @Override
    public void deleteSportHouse(Long id) {
        sportHouseRepository.deleteById(id);
    }
}
