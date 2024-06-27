package com.shuaibu.controller;

import com.shuaibu.dto.SchoolClassDto;
import com.shuaibu.dto.SectionDto;
import com.shuaibu.mapper.UserMapper;
import com.shuaibu.model.UserModel;
import com.shuaibu.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.StudentDto;
import com.shuaibu.model.StudentModel;

import jakarta.validation.Valid;

import java.util.Collections;

@Controller
@RequestMapping("/students")
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {
    
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final TermService termService;
    private final SportHouseService sportHouseService;
    private final UserService userService;
    private final SectionService sectionService;

    public StudentController(StudentService studentService, SchoolClassService schoolClassService, TermService termService, SportHouseService sportHouseService, UserService userService, SectionService sectionService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.termService = termService;
        this.sportHouseService = sportHouseService;
        this.userService = userService;
        this.sectionService = sectionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public String listStudents(Model model) {
        model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
        model.addAttribute("termIds", termService.getAllTerms());
        model.addAttribute("sectionIds", sectionService.getAllSections());
        model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
        model.addAttribute("student", new StudentModel());
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public String createStudentForm(Model model) {
        model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
        model.addAttribute("termIds", termService.getAllTerms());
        model.addAttribute("sectionIds", sectionService.getAllSections());
        model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
        model.addAttribute("student", new StudentModel());
        return "students/new";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public String saveStudent(@Valid @ModelAttribute("student") StudentDto student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
            model.addAttribute("termIds", termService.getAllTerms());
            model.addAttribute("sectionIds", sectionService.getAllSections());
            model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
            model.addAttribute("student", student);
            return "students/new";
        }

        UserModel userModel = new UserModel();
        userModel.setUsername(student.getUserName());
        userModel.setPassword(student.getPassword());
        userModel.setRoles(Collections.singleton("ROLE_STUDENT"));

        userService.saveUser(UserMapper.mapToDto(userModel));

        studentService.saveOrUpdateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model) {
        StudentDto student = studentService.getStudentById(id);
        model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
        model.addAttribute("termIds", termService.getAllTerms());
        model.addAttribute("sectionIds", sectionService.getAllSections());
        model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
        model.addAttribute("student", student);
        return "students/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentDto student, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
            model.addAttribute("termIds", termService.getAllTerms());
            model.addAttribute("sectionIds", sectionService.getAllSections());
            model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
            model.addAttribute("student", student);
            return "students/edit";
        }
        student.setId(id);
        studentService.saveOrUpdateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
