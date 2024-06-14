package com.shuaibu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shuaibu.model.PermissionModel;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionModel, Long> {
}

