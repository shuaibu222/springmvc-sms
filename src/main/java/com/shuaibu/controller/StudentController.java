package com.shuaibu.controller;

import com.shuaibu.dto.*;
import com.shuaibu.model.*;
import com.shuaibu.repository.*;
import com.shuaibu.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Arrays;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/students")
@PreAuthorize("hasRole('ADMIN')")
public class StudentController {

    // private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final TermService termService;
    private final SportHouseService sportHouseService;
    private final SectionService sectionService;
    private final StudentRepository studentRepository;
    private final SessionService sessionService;

    public StudentController(PasswordEncoder passwordEncoder, StudentService studentService, SchoolClassService schoolClassService, TermService termService, SportHouseService sportHouseService, UserService userService, SectionService sectionService, SchoolClassRepository schoolClassRepository, SectionRepository sectionRepository, TermRepository termRepository, SportHouseRepository sportHouseRepository, UserRepository userRepository, StudentRepository studentRepository, SessionService sessionService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.termService = termService;
        this.sportHouseService = sportHouseService;
        this.sectionService = sectionService;
        this.studentRepository = studentRepository;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
        model.addAttribute("termIds", termService.getAllTerms());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("sectionIds", sectionService.getAllSections());
        model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
        model.addAttribute("student", new StudentModel());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));
        return "students/list";
    }

    @PostMapping
    public String saveListStudent(@Valid @ModelAttribute StudentDto student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
            model.addAttribute("termIds", termService.getAllTerms());
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("sectionIds", sectionService.getAllSections());
            model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));
            return "students/list";
        }

        studentService.saveOrUpdateStudent(student);
        return "redirect:/students?success";
    }

    @GetMapping("/new")
    public String createStudentForm(Model model) {
        model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
        model.addAttribute("termIds", termService.getAllTerms());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("sectionIds", sectionService.getAllSections());
        model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
        model.addAttribute("student", new StudentModel());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));
        return "students/new";
    }

    @PostMapping("/create")
    public String saveStudent(@Valid @ModelAttribute StudentDto student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("studentClassIds", schoolClassService.getAllSchoolClass());
            model.addAttribute("termIds", termService.getAllTerms());
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("sectionIds", sectionService.getAllSections());
            model.addAttribute("sportHouseIds", sportHouseService.getAllSportHouses());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));
            model.addAttribute("student", student);
            return "students/new";
        }

        studentService.saveOrUpdateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/details/{id}")
    public String getStudentDetails(@PathVariable Long id, Model model) {
        StudentDto student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "students/details";
    }

    @GetMapping("/edit/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model) {
        StudentDto student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        // Add other data required for the dropdowns
        model.addAttribute("allClasses", schoolClassService.getAllSchoolClass());
        model.addAttribute("allSections", sectionService.getAllSections());
        model.addAttribute("allTerms", termService.getAllTerms());
        model.addAttribute("academicSessions", sessionService.getAllSessions());
        model.addAttribute("allSportHouses", sportHouseService.getAllSportHouses());
        model.addAttribute("gender", Arrays.asList("Male", "Female"));
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));

        return "students/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute StudentDto student,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {

            // Add other data required for the dropdowns
            model.addAttribute("allClasses", schoolClassService.getAllSchoolClass());
            model.addAttribute("allSections", sectionService.getAllSections());
            model.addAttribute("allTerms", termService.getAllTerms());
            model.addAttribute("academicSessions", sessionService.getAllSessions());
            model.addAttribute("allSportHouses", sportHouseService.getAllSportHouses());
            model.addAttribute("gender", Arrays.asList("Male", "Female"));
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            model.addAttribute("admissionType", Arrays.asList("Fully-funded", "Partial-scholarship", "Fully-scholarship"));

            return "students/edit";
        }

        // Update overall student
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
