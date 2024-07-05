package com.shuaibu.controller;

import com.shuaibu.dto.*;
import com.shuaibu.mapper.*;
import com.shuaibu.model.SchoolClassModel;
import com.shuaibu.model.UserModel;
import com.shuaibu.repository.SchoolClassRepository;
import com.shuaibu.repository.SectionRepository;
import com.shuaibu.repository.SportHouseRepository;
import com.shuaibu.repository.TermRepository;
import com.shuaibu.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.model.StudentModel;

import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("ALL")
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
    private final SchoolClassRepository schoolClassRepository;
    private final SectionRepository sectionRepository;
    private final TermRepository termRepository;
    private final SportHouseRepository sportHouseRepository;

    public StudentController(StudentService studentService, SchoolClassService schoolClassService, TermService termService, SportHouseService sportHouseService, UserService userService, SectionService sectionService, SchoolClassRepository schoolClassRepository, SectionRepository sectionRepository, TermRepository termRepository, SportHouseRepository sportHouseRepository) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.termService = termService;
        this.sportHouseService = sportHouseService;
        this.userService = userService;
        this.sectionService = sectionService;
        this.schoolClassRepository = schoolClassRepository;
        this.sectionRepository = sectionRepository;
        this.termRepository = termRepository;
        this.sportHouseRepository = sportHouseRepository;
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

        // fetch specific datas
        SchoolClassDto schoolClassDto = SchoolClassMapper.mapToDto(schoolClassRepository.findSchoolClassModelByClassNameAndSectionId(student.getStudentClassId(), student.getSectionId()));
        SectionDto sectionDto = SectionMapper.mapToDto(sectionRepository.findBySectionName(student.getSectionId()));
        TermDto termDto = TermMapper.mapToDto(termRepository.findByTermName(student.getTermId()));
        SportHouseDto sportHouseDto = SportHouseMapper.mapToDto(sportHouseRepository.findBySportHouseName(student.getSportHouseId()));

        model.addAttribute("studentClassIds", Arrays.asList(schoolClassDto));
        model.addAttribute("termIds", termDto);
        model.addAttribute("sectionIds", sectionDto);
        model.addAttribute("sportHouseIds", sportHouseDto);
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
