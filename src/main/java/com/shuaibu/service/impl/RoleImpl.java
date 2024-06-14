package com.shuaibu.service.impl;

import org.springframework.stereotype.Service;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;
import com.shuaibu.repository.RoleRepository;
import com.shuaibu.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

import static com.shuaibu.mapper.RoleMapper.*;

@Service
public class RoleImpl implements RoleService {
    
    private RoleRepository roleRepository;

    public RoleImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleModel> roles = roleRepository.findAll();
        return roles.stream().map(role -> mapToDto(role)).collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Long id) {
        return mapToDto(roleRepository.findById(id).get());
    }

    @Override
    public RoleModel saveRole(RoleDto roleDto) {
        return roleRepository.save(mapToModel(roleDto));
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        roleRepository.save(mapToModel(roleDto));
    }
    
    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
