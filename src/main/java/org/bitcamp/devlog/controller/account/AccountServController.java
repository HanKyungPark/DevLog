package org.bitcamp.devlog.controller.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountServController {
    private final AccountService accountService;

    @GetMapping("/save")
    public String saveUser(@ModelAttribute Account account) {
        return "templates/userlist";
    }

    /**
     * 카카오에서 이메일,이름,프로필사진 받아온다
     * 받은 데이터로 account객체를 만든다.
     * account객체를 이용해 db에저장한다.
     */

    @GetMapping("/mypage")
    public String mypage(
            Model model
    ) {
        return "contents/myPage";
    }

    @GetMapping("/feed")
    public String feed() {
        return "contents/feedPage";
    }

    @GetMapping("/home")
    public String home() {
        return "contents/homePage";
    }

    @GetMapping("/home2")
    public String home2() {
        return "contents/homePage2";
    }

    @GetMapping("/check")
    public String check() {
        return accountService.findByEmail();
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", oauth2User.getUsername());
        model.addAttribute("email", oauth2User.getEmail());

        return "contents/loginForm";
    }

    @GetMapping("/{homepage}")
    public String mypage(@PathVariable String homepage) {
        return "contents/myBlogPage";
    }

    @GetMapping("/loginUpdateForm")
    public String loginUpdateForm(Model model) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", oauth2User.getUsername());
        model.addAttribute("email", oauth2User.getEmail());


        return "contents/loginForm";
    }

    @GetMapping("/account/update")
    public String accountUpdate(Model model) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long accountId = oauth2User.getAccountId();

        List<Account> list = new ArrayList<>();
        list.add(accountService.findByAccountId(accountId));

        model.addAttribute("name", oauth2User.getUsername());
        model.addAttribute("email", oauth2User.getEmail());
        model.addAttribute("accountId", accountId);
        model.addAttribute("biography", list.get(0).getBiography());
        model.addAttribute("homepage", list.get(0).getHomepage());
        model.addAttribute("file", list.get(0).getFile());
        model.addAttribute("blog_id", list.get(0).getBlogId());

        return "contents/loginUpdateForm";

    }

    ;
}