package com.shuaibu.mapper;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;

public class RoleMapper {
    
    public static RoleDto mapToDto(RoleModel roleModel){

        return RoleDto.builder()
        .id(roleModel.getId())
        .roleName(roleModel.getRoleName())
        .build();
    }

    public static RoleModel mapToModel(RoleDto roleDto){

        return RoleModel.builder()
        .id(roleDto.getId())
        .roleName(roleDto.getRoleName())
        .build();
    }
}
