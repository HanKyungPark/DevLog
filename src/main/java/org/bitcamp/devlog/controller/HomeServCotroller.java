
package org.bitcamp.devlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeServCotroller {

    @GetMapping("/")
    public String home() {
        return "contents/myPage";
    }

    @GetMapping("/login")
    public String Home() {
        return "index";
    }

}

