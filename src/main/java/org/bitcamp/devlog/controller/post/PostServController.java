package org.bitcamp.devlog.controller.post;

import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostServController {
    @Autowired
    AccountService accountService;
    @GetMapping("/contents")
    public String post()
    {
        return "contents/myBlogPage";
    }
    @GetMapping("/post")
    public String posting()
    {
        return "contents/postingForm";
    }
    @GetMapping("/newblogs")
    public String newblogs(Model model) {
        List<Account> list=accountService.findAll();
        model.addAttribute("list",list);
        return "contents/newBlogs";
    }


}
