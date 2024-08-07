package com.shuaibu.controller;

import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.StaffRepository;
import com.shuaibu.repository.SubjectRepository;
import com.shuaibu.repository.UserRepository;
import com.shuaibu.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StaffDto;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.StaffModel;
import com.shuaibu.model.SubjectModel;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/staffs")
@PreAuthorize("hasRole('ADMIN')")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
    private final StaffService staffService;
    private final SubjectService subjectService;
    private final SchoolClassService schoolClassService;
    private final StaffRepository staffRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;

    public StaffController(StaffService staffService, SubjectService subjectService, SchoolClassService schoolClassService, UserService userService, UserRepository userRepository, SchoolClassRepository schoolClassRepository, StaffRepository staffRepository, SubjectRepository subjectRepository, SchoolClassRepository schoolClassRepository2) {
        this.staffService = staffService;
        this.subjectService = subjectService;
        this.schoolClassService = schoolClassService;
        this.staffRepository = staffRepository;
        this.subjectRepository = subjectRepository;
        this.schoolClassRepository = schoolClassRepository2;
    }

    

    @GetMapping
    public String listStaffs(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("staffs", staffRepository.findAll());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "staffs/list";
    }

    @PostMapping
    public String saveListStaff(@Valid @ModelAttribute StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

            return "staffs/list";
        }

        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs?success";
    }

    @GetMapping("/new")
    public String createStaffForm(Model model) {
        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("staff", new StaffModel());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        
        return "staffs/new";
    }

    @PostMapping("/create")
    public String saveStaff(@Valid @ModelAttribute StaffDto staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("staff", staff);
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

            return "staffs/new";
        }
        
        staffService.saveOrUpdateStaff(staff);
        return "redirect:/staffs";
    }

    @GetMapping("/details/{id}")
    public String getStaffDetails(@PathVariable Long id, Model model) {
        StaffDto staff = staffService.getStaffById(id);

        // get the staff subjects
        List<SubjectModel> subjectModels = new ArrayList<>();

        for (Long subjectId : staff.getSubjectModelIds()) {
            SubjectModel subjectModel = subjectRepository.findById(subjectId).get();
            if (subjectModel != null) {
                subjectModels.add(subjectModel);
            }
        }

        // get the staff class models
        List<SchoolClassModel> schoolClassModels = new ArrayList<>();

        for (Long schoolClassId : staff.getClassModelIds()) {
            SchoolClassModel schoolClassModel = schoolClassRepository.findById(schoolClassId).get();
            if (schoolClassModel != null) {
                schoolClassModels.add(schoolClassModel);
            }
        }

        model.addAttribute("subjects", subjectModels);
        model.addAttribute("classes", schoolClassModels);
        model.addAttribute("staff", staff);

        return "staffs/details";
    }


    @GetMapping("/edit/{id}")
    public String updateStaffForm(@PathVariable Long id, Model model) {
        StaffDto staff = staffService.getStaffById(id);

        model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("staff", staff);
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));

        return "staffs/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable Long id,
                                @Valid @ModelAttribute StaffDto staff, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classModels", schoolClassService.getAllSchoolClass());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("staff", staff);
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));

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
