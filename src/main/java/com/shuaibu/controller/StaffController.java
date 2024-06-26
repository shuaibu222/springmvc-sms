package com.shuaibu.controller;

import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.UserModel;
import com.shuaibu.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.model.SubjectModel;

import jakarta.validation.Valid;

import java.util.Collections;

@Controller
@RequestMapping("/staffs")
@PreAuthorize("hasRole('ADMIN')")
public class StaffController {
    
    private final StaffService staffService;
    private final SubjectService subjectService;
    private final SchoolClassService schoolClassService;
    private final RoleService roleService;
    private final UserService userService;

    public StaffController(StaffService staffService, SubjectService subjectService, SchoolClassService schoolClassService, RoleService roleService, UserService userService) {
        this.staffService = staffService;
        this.subjectService = subjectService;
        this.schoolClassService = schoolClassService;
        this.roleService = roleService;
        this.userService = userService;
    }

    

    @GetMapping
    public String listStaffs(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("staffs", staffService.getAllStaffs());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "staffs/list";
    }

    @GetMapping("/new")
    public String createStaffForm(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("roles", roleService.getAllRoles());
        
        return "staffs/new";
    }

    @PostMapping("/create")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("staff", staff);
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("roles", roleService.getAllRoles());

            return "staffs/new";
        }

        UserModel userModel = new UserModel();
        userModel.setUsername(staff.getUserName());
        userModel.setPassword(staff.getPassword());
        userModel.setRoles(Collections.singleton("ROLE_STAFF"));

        userService.saveUser(UserMapper.mapToDto(userModel));
        
        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/edit/{id}")
    public String updateStaffForm(@PathVariable Long id, Model model) {
        StaffDto staff = staffService.getStaffById(id);
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("staff", staff);
        model.addAttribute("roles", roleService.getAllRoles());

        return "staffs/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id,
                                @Valid @ModelAttribute("staff") StaffDto staff, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("staff", staff);
            model.addAttribute("roles", roleService.getAllRoles());

            return "staffs/edit";
        }
        staff.setId(id);
        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staffs";
    }
}
