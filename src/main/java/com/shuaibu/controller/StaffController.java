package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.service.StaffService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/staffs")
public class StaffController {
    
    private StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String listStaffs(Model model) {
        model.addAttribute("staffs", staffService.getAllStaffs());
        return "staffs/list";
    }

    @GetMapping("/new")
    public String createStaffForm(Model model) {
        model.addAttribute("staff", new StaffModel());
        return "staffs/new";
    }

    @PostMapping("/create")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staff", staff);
            return "staffs/new";
        }
        staffService.saveStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/edit/{id}")
    public String updateStaffForm(@PathVariable Long id, Model model) {
        StaffDto staff = staffService.getStaffById(id);
        model.addAttribute("staff", staff);
        return "staffs/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id,
                                @Valid @ModelAttribute("staff") StaffDto staff, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staff", staff);
            return "staffs/edit";
        }
        staff.setId(id);
        staffService.updateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staffs";
    }
}
