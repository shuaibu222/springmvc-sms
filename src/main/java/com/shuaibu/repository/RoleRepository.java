package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
}

