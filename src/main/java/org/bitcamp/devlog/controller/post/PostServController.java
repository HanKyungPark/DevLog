package org.bitcamp.devlog.controller.post;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.service.*;

import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.PostService;

import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.service.*;



import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostServController {

    private final PostService postService;
    private final PostTagService postTagService;
    private final TagService tagService;
    private final AccountService accountService;

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

        List<Object> tag = new ArrayList<>();
        tag = postService.findPost_namebypostUrl(postUrl);
        model.addAttribute("post",tag);


        System.out.println(tag);

        return "contents/postingDetail";
    }

    //마이페이지에서 수정페이지로 이동
    @GetMapping("/mypage/post/update/{postUrl}")
    public String mypageToPostUpdatePage(
        @PathVariable String postUrl
    ){
        return "contents/updateForm";
    }




}
