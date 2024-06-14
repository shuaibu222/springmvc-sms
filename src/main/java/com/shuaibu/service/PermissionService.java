package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.PermissionDto;
import com.shuaibu.model.PermissionModel;

public interface PermissionService {
    List<PermissionDto> getAllPermissions();
    PermissionDto getPermissionById(Long id);
    PermissionModel savePermission(PermissionDto permissionDto);
    void updatePermission(PermissionDto permissionDto);
    void deletePermission(Long id);
}
