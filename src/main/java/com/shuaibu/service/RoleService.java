package com.shuaibu.service;

import java.util.List;

import com.shuaibu.dto.RoleDto;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Long id);
    void saveOrUpdateRole(RoleDto roleDto);
    void deleteRole(Long id);
}
