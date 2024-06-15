package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.PermissionDto;
import com.shuaibu.model.PermissionModel;
import com.shuaibu.repository.PermissionRepository;
import com.shuaibu.service.PermissionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.PermissionMapper.*;

@Service
public class PermissionImpl implements PermissionService {
    
    private PermissionRepository permissionRepository;

    public PermissionImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<PermissionDto> getAllPermissions() {
        List<PermissionModel> permissions = permissionRepository.findAll();
        return permissions.stream().map(permission -> mapToDto(permission)).collect(Collectors.toList());
    }

    @Override
    public PermissionDto getPermissionById(UUID id) {
        return mapToDto(permissionRepository.findById(id).get());
    }

    @Override
    public PermissionModel savePermission(PermissionDto permissionDto) {
        return permissionRepository.save(mapToModel(permissionDto));
    }

    @Override
    public void updatePermission(PermissionDto permissionDto) {
        permissionRepository.save(mapToModel(permissionDto));
    }
    
    @Override
    public void deletePermission(UUID id) {
        permissionRepository.deleteById(id);
    }
}
