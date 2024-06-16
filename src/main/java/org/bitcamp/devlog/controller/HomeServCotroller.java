package org.bitcamp.devlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeServCotroller {
    @GetMapping("/")
    public String home() {
        return "layout/layout_ele/header";
    }
}
