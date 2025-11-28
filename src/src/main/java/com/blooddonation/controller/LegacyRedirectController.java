package com.blooddonation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LegacyRedirectController {

    // Redirect legacy .php URLs to the same path without extension, e.g., /register.php -> /register
    @GetMapping("/{page}.php")
    public String redirectPhp(@PathVariable String page) {
        return "redirect:/" + page;
    }
}

