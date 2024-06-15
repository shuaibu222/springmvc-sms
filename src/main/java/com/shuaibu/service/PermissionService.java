package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.PermissionDto;
import com.shuaibu.model.PermissionModel;

public interface PermissionService {
    List<PermissionDto> getAllPermissions();
    PermissionDto getPermissionById(UUID id);
    PermissionModel savePermission(PermissionDto permissionDto);
    void updatePermission(PermissionDto permissionDto);
    void deletePermission(UUID id);
}
