package com.shuaibu.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.PermissionDto;
import com.shuaibu.model.PermissionModel;
import com.shuaibu.service.PermissionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/permissions")
public class PermissionController {
    
    private PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public String listPermissions(Model model) {
        model.addAttribute("permissions", permissionService.getAllPermissions());
        return "permissions/list";
    }

    @GetMapping("/new")
    public String createPermissionForm(Model model) {
        model.addAttribute("permission", new PermissionModel());
        return "permissions/new";
    }

    @PostMapping("/create")
    public String savePermission(@Valid @ModelAttribute("permission") PermissionDto permission, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("permission", permission);
            return "permissions/new";
        }
        permissionService.savePermission(permission);
        return "redirect:/permissions";
    }

    @GetMapping("/edit/{id}")
    public String updatePermissionForm(@PathVariable UUID id, Model model) {
        PermissionDto permission = permissionService.getPermissionById(id);
        model.addAttribute("permission", permission);
        return "permissions/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePermission(@PathVariable UUID id,
                                @Valid @ModelAttribute("permission") PermissionDto permission, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("permission", permission);
            return "permissions/edit";
        }
        permission.setId(id);
        permissionService.updatePermission(permission);
        return "redirect:/permissions";
    }

    @GetMapping("/delete/{id}")
    public String deletePermission(@PathVariable UUID id) {
        permissionService.deletePermission(id);
        return "redirect:/permissions";
    }
}
