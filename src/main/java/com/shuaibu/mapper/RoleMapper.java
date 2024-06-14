package com.shuaibu.mapper;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;

public class RoleMapper {
    
    public static RoleDto mapToDto(RoleModel roleModel){
        RoleDto roleDto = RoleDto.builder()
        .id(roleModel.getId())
        .roleName(roleModel.getRoleName())
        .build();

        return roleDto;
    }

    public static RoleModel mapToModel(RoleDto roleDto){
        RoleModel roleModel =RoleModel.builder()
        .id(roleDto.getId())
        .roleName(roleDto.getRoleName())
        .build();

        return roleModel;
    }
}
