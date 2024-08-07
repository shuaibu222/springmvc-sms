package com.shuaibu.controller;

import com.shuaibu.repository.ClassTeacherRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.ClassTeacherCommentDto;
import com.shuaibu.model.ClassTeacherCommentModel;
import com.shuaibu.service.ClassTeacherCommentService;

import jakarta.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/classTeacherComments")
@PreAuthorize("hasRole('ADMIN')")
public class ClassTeacherCommentController {
    
    private final ClassTeacherCommentService classTeacherCommentService;
    private final ClassTeacherRepository classTeacherRepository;

    public ClassTeacherCommentController(ClassTeacherCommentService classTeacherCommentService, ClassTeacherRepository classTeacherRepository) {
        this.classTeacherCommentService = classTeacherCommentService;
        this.classTeacherRepository = classTeacherRepository;
    }

    @GetMapping
    public String classTeacherCommentsForm(Model model) {
        model.addAttribute("classTeacherCommentDto", new ClassTeacherCommentModel());
        model.addAttribute("classTeachers", classTeacherRepository.findAll());
        model.addAttribute("classTeacherComments", classTeacherCommentService.getAllClassTeacherComments());
        return "classTeacherComments/list";
    }

    @PostMapping
    public String saveClassTeacherComment(@Valid @ModelAttribute("classTeacherCommentDto") ClassTeacherCommentDto classTeacherComment,
                                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classTeachers", classTeacherRepository.findAll());
            model.addAttribute("classTeacherComments", classTeacherCommentService.getAllClassTeacherComments());
            return "classTeacherComments/list";
        }
        classTeacherCommentService.saveOrUpdateClassTeacherComment(classTeacherComment);
        return "redirect:/classTeacherComments?success";
    }

    @GetMapping("/edit/{id}")
    public String updateClassTeacherCommentForm(@PathVariable Long id, Model model) {
        ClassTeacherCommentDto classTeacherComment = classTeacherCommentService.getClassTeacherCommentById(id);
        model.addAttribute("classTeachers", classTeacherRepository.findAll());
        model.addAttribute("classTeacherComment", classTeacherComment);
        return "classTeacherComments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateClassTeacherComment(@PathVariable Long id,
                                @Valid @ModelAttribute ClassTeacherCommentDto classTeacherComment, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classTeachers", classTeacherRepository.findAll());
            model.addAttribute("classTeacherComments", classTeacherCommentService.getAllClassTeacherComments());
            return "classTeacherComments/edit";
        }
        classTeacherComment.setId(id);
        classTeacherCommentService.saveOrUpdateClassTeacherComment(classTeacherComment);
        return "redirect:/classTeacherComments";
    }

    @GetMapping("/delete/{id}")
    public String deleteClassTeacherComment(@PathVariable Long id) {
        classTeacherCommentService.deleteClassTeacherComment(id);
        return "redirect:/classTeacherComments";
    }
}
