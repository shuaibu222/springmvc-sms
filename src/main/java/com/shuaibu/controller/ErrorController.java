package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SuppressWarnings("SameReturnValue")
@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "errors/error";
    }
}
