package com.shuaibu.mapper;

import com.shuaibu.dto.PermissionDto;
import com.shuaibu.model.PermissionModel;

public class PermissionMapper {
    
    public static PermissionDto mapToDto(PermissionModel permissionModel){
        PermissionDto permissionDto = PermissionDto.builder()
        .id(permissionModel.getId())
        .permissionName(permissionModel.getPermissionName())
        .build();

        return permissionDto;
    }

    public static PermissionModel mapToModel(PermissionDto permissionDto){
        PermissionModel permissionModel =PermissionModel.builder()
        .id(permissionDto.getId())
        .permissionName(permissionDto.getPermissionName())
        .build();

        return permissionModel;
    }
}
