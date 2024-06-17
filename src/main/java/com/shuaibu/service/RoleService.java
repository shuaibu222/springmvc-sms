package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleModel saveRole(RoleDto roleDto);
    void updateRole(RoleDto roleDto);
    void deleteRole(Long id);
}
