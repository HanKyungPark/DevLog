package org.bitcamp.devlog.controller.post;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PostServController {


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
    public String newblogs() {
        return "contents/newBlogs";
    }

}
