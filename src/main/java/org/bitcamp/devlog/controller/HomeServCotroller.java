
package org.bitcamp.devlog.controller;

import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeServCotroller {

    @GetMapping("/")
    public String home() {
        return "contents/mainPage";
    }


}

