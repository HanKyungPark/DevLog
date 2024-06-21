package org.bitcamp.devlog.controller.post;

import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.service.PostService;
import org.bitcamp.devlog.service.PostTagService;
import org.bitcamp.devlog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostServController {
    private final PostService postService;
    private final PostTagService postTagService;
    private final TagService tagService;

    public PostServController(PostService postService, PostTagService postTagService, TagService tagService) {
        this.postService = postService;
        this.postTagService = postTagService;
        this.tagService = tagService;
    }

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

    @GetMapping("/detail")
    public String detail(@RequestParam String postUrl, Model model) {

        List<Object> tag=new ArrayList<>();
        tag=postService.findPost_namebypostUrl(postUrl);
        model.addAttribute("post",tag);
        System.out.println(tag);
        return "contents/postingDetail";
    }

}
