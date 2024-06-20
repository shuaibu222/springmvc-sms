package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    RoleModel saveOrUpdateRole(RoleDto roleDto);
    void deleteRole(Long id);
}
