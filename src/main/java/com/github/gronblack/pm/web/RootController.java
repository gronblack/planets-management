package com.github.gronblack.pm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/lords")
    public String lords() {
        return "lords";
    }

    @GetMapping("/planets")
    public String planets() {
        return "planets";
    }
}
