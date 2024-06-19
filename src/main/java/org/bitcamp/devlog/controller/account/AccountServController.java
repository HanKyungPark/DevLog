package org.bitcamp.devlog.controller.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String mypage()
    {
        return "contents/myPage";
    }

    @GetMapping("/feed")
    public String feed()
    {
        return "contents/feedPage";
    }

    @GetMapping("/home")
    public String home()
    {
        return "contents/homePage";
    }
    @GetMapping("/home2")
    public String home2()
    {
        return "contents/homePage2";
    }

    @GetMapping("/check")
    public String check(){
        return accountService.findByEmail();
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "contents/loginForm";
    }

}
