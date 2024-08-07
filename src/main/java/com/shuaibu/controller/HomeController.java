package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SuppressWarnings("SameReturnValue")
@Controller("/")
public class HomeController {

    @GetMapping("/")
    public String HomePage() {
        return "home/home_page";
    }

}
