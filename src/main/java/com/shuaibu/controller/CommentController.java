package com.shuaibu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.CommentDto;
import com.shuaibu.model.CommentModel;
import com.shuaibu.service.CommentService;

import jakarta.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
@RequestMapping("/comments")
@PreAuthorize("hasRole('ADMIN')")
public class CommentController {
    
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public String listComments(Model model) {
        model.addAttribute("comment", new CommentModel());
        model.addAttribute("comments", commentService.getAllComments());
        return "comments/list";
    }

    @PostMapping
    public String saveListComment(@Valid @ModelAttribute("comment") CommentDto comment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comments", commentService.getAllComments());
            return "comments/list";
        }
        commentService.saveOrUpdateComment(comment);
        return "redirect:/comments";
    }

    @GetMapping("/new")
    public String createCommentForm(Model model) {
        model.addAttribute("comment", new CommentModel());
        return "comments/new";
    }

    @PostMapping("/create")
    public String saveComment(@Valid @ModelAttribute("comment") CommentDto comment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comment", comment);
            return "comments/new";
        }
        commentService.saveOrUpdateComment(comment);
        return "redirect:/comments";
    }

    @GetMapping("/edit/{id}")
    public String updateCommentForm(@PathVariable Long id, Model model) {
        CommentDto comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "comments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id,
                                @Valid @ModelAttribute("comment") CommentDto comment, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comment", comment);
            return "comments/edit";
        }
        comment.setId(id);
        commentService.saveOrUpdateComment(comment);
        return "redirect:/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/comments";
    }
}
