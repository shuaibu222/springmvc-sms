package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.UserDto;
import com.shuaibu.model.UserModel;
import com.shuaibu.service.UserService;

import jakarta.validation.Valid;

import java.util.Collections;

@Controller
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String createLoginForm() {
        return "users/login";
    }

    @GetMapping("/register")
    public String createRegisterForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "users/register";
        }

        // Ensure that a role is set, for example:
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton("ROLE_USER"));
        }

        userService.saveUser(user);
        return "redirect:/login?success";
    }

}
