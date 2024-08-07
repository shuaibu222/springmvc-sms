package com.shuaibu.controller;

import com.shuaibu.dto.PromotionDto;
import com.shuaibu.dto.StudentDto;
import com.shuaibu.repository.StudentRepository;
import com.shuaibu.service.SchoolClassService;
import com.shuaibu.service.SectionService;
import com.shuaibu.service.StudentService;
import com.shuaibu.service.impl.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;
    private final SchoolClassService schoolClassService;
    private final SectionService sectionService;
    private final StudentRepository studentRepository;

    public PromotionController(PromotionService promotionService, StudentService studentService, SchoolClassService schoolClassService, SectionService sectionService, StudentRepository studentRepository) {
        this.promotionService = promotionService;
        this.schoolClassService = schoolClassService;
        this.sectionService = sectionService;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String showPromotionForm(Model model) {
        model.addAttribute("promotion", new PromotionDto());
        model.addAttribute("classes", schoolClassService.getAllSchoolClass());
        model.addAttribute("sections", sectionService.getAllSections());
        return "promotions/promote";
    }

    @GetMapping("/students/{classId}")
    @ResponseBody
    public List<StudentDto> getStudentsByClass(@PathVariable Long classId) {
        return studentRepository.findByStudentClassIdAndIsActive(String.valueOf(classId), "True");
    }

    @PostMapping("/promote")
    public String promoteStudents(@RequestParam List<Long> studentIds, @RequestParam Long toClassId) {
        if (studentIds == null || studentIds.isEmpty()) {
            throw new IllegalArgumentException("Student IDs cannot be null or empty");
        }
        promotionService.promoteStudents(studentIds, toClassId);
        return "redirect:/promotions?success";
    }

}
