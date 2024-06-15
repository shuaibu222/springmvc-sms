package com.shuaibu.service;

import java.util.List;
import java.util.UUID;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(UUID id);
    RoleModel saveRole(RoleDto roleDto);
    void updateRole(RoleDto roleDto);
    void deleteRole(UUID id);
}
