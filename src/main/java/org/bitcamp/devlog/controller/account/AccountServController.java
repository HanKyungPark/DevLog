package org.bitcamp.devlog.controller.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class AccountServController {



    @GetMapping("/save")
    public String saveUser(@ModelAttribute Account account) {



        return "templates/userlist";
    }
    /**
     * 카카오에서 이메일,이름,프로필사진 받아온다
     * 받은 데이터로 account객체를 만든다.
     * account객체를 이용해 db에저장한다.
     */

}
