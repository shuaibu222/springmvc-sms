package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.RoleDto;
import com.shuaibu.model.RoleModel;
import com.shuaibu.service.RoleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {
    
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "roles/list";
    }

    @GetMapping("/new")
    public String createRoleForm(Model model) {
        model.addAttribute("role", new RoleModel());
        return "roles/new";
    }

    @PostMapping("/create")
    public String saveRole(@Valid @ModelAttribute("role") RoleDto role, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("role", role);
            return "roles/new";
        }
        roleService.saveRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/edit/{id}")
    public String updateRoleForm(@PathVariable Long id, Model model) {
        RoleDto role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "roles/edit";
    }

    @PostMapping("/update/{id}")
    public String updateRole(@PathVariable Long id,
                                @Valid @ModelAttribute("role") RoleDto role, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("role", role);
            return "roles/edit";
        }
        role.setId(id);
        roleService.updateRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }
}
