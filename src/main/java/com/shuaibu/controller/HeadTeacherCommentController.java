package com.shuaibu.controller;

import com.shuaibu.dto.HeadTeacherCommentDto;
import com.shuaibu.model.HeadTeacherCommentModel;
import com.shuaibu.repository.HeadTeacherRepository;
import com.shuaibu.service.HeadTeacherCommentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/headTeacherComments")
@PreAuthorize("hasRole('ADMIN')")
public class HeadTeacherCommentController {

    private final HeadTeacherCommentService headTeacherCommentService;
    private final HeadTeacherRepository headTeacherRepository;

    public HeadTeacherCommentController(HeadTeacherCommentService headTeacherCommentService, HeadTeacherRepository headTeacherRepository) {
        this.headTeacherCommentService = headTeacherCommentService;
        this.headTeacherRepository = headTeacherRepository;
    }

    @GetMapping
    public String headTeacherCommentsForm(Model model) {
        model.addAttribute("headTeacherCommentDto", new HeadTeacherCommentModel());
        model.addAttribute("headTeachers", headTeacherRepository.findAll());
        model.addAttribute("headTeacherComments", headTeacherCommentService.getAllHeadTeacherComments());
        return "headTeacherComments/list";
    }

    @PostMapping
    public String saveHeadTeacherComment(@Valid @ModelAttribute("headTeacherCommentDto") HeadTeacherCommentDto headTeacherComment,
                                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("headTeachers", headTeacherRepository.findAll());
            model.addAttribute("headTeacherComments", headTeacherCommentService.getAllHeadTeacherComments());
            return "headTeacherComments/list";
        }
        headTeacherCommentService.saveOrUpdateHeadTeacherComment(headTeacherComment);
        return "redirect:/headTeacherComments";
    }

    @GetMapping("/edit/{id}")
    public String updateHeadTeacherCommentForm(@PathVariable Long id, Model model) {
        HeadTeacherCommentDto headTeacherComment = headTeacherCommentService.getHeadTeacherCommentById(id);
        model.addAttribute("headTeachers", headTeacherRepository.findAll());
        model.addAttribute("headTeacherComment", headTeacherComment);
        return "headTeacherComments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateHeadTeacherComment(@PathVariable Long id,
                                @Valid @ModelAttribute("headTeacherComment") HeadTeacherCommentDto headTeacherComment,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("headTeachers", headTeacherRepository.findAll());
            model.addAttribute("headTeacherComments", headTeacherCommentService.getAllHeadTeacherComments());
            return "headTeacherComments/edit";
        }
        headTeacherComment.setId(id);
        headTeacherCommentService.saveOrUpdateHeadTeacherComment(headTeacherComment);
        return "redirect:/headTeacherComments";
    }

    @GetMapping("/delete/{id}")
    public String deleteHeadTeacherComment(@PathVariable Long id) {
        headTeacherCommentService.deleteHeadTeacherComment(id);
        return "redirect:/headTeacherComments";
    }
}
