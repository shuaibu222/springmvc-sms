package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.StaffModel;

@Repository
public interface StaffRepository extends JpaRepository<StaffModel, Long> {
}

