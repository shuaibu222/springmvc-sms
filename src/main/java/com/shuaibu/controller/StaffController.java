package com.shuaibu.controller;

import java.util.List;
import java.util.UUID;

import javax.security.auth.Subject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.dto.SubjectDto;
import com.shuaibu.model.StaffModel;
import com.shuaibu.model.SubjectModel;
import com.shuaibu.service.StaffService;
import com.shuaibu.service.SubjectService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/staffs")
public class StaffController {
    
    private StaffService staffService;
    private SubjectService subjectService;

    public StaffController(StaffService staffService, SubjectService subjectService) {
        this.staffService = staffService;
        this.subjectService = subjectService;
    }

    

    @GetMapping
    public String listStaffs(Model model) {
        model.addAttribute("staffs", staffService.getAllStaffs());
        return "staffs/list";
    }

    @GetMapping("/new")
    public String createStaffForm(Model model) {
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        
        return "staffs/new";
    }

    @PostMapping("/create")
    public String saveStaff(@Valid @ModelAttribute("staff") StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("staff", staff);
            model.addAttribute("subjects", new SubjectModel());
            return "staffs/new";
        }
        
        staffService.saveStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/edit/{id}")
    public String updateStaffForm(@PathVariable UUID id, Model model) {
        StaffDto staff = staffService.getStaffById(id);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("staff", staff);
        return "staffs/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable UUID id,
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
    public String deleteStaff(@PathVariable UUID id) {
        staffService.deleteStaff(id);
        return "redirect:/staffs";
    }
}
