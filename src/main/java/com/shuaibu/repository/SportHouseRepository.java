package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.SportHouseModel;

@Repository
public interface SportHouseRepository extends JpaRepository<SportHouseModel, Long> {
    SportHouseModel findBySportHouseName(String sportHouseId);
}

