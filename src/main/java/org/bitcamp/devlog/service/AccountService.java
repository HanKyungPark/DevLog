package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountMapper accountMapper;

    public void save(Account account) {
        accountMapper.save(account);

    }
    public void update(Account account) {
        account.setAccountId(((Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccountId());
        accountMapper.update(account);
    }

    public String findByEmail(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(email);;
        Account account = accountMapper.findByEmail(email);

        if(account.getBlogId() == null){
            return "redirect:/loginForm";
        } else {
            return "redirect:/home";
        }
    }
}
