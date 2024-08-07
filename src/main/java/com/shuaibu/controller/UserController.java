package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("ALL")
@Controller
public class UserController {

    @GetMapping("/login")
    public String createLoginForm() {
        return "users/login";
    }

}
