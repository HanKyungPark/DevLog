package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.mapper.AccountMapper;
import org.bitcamp.devlog.mapper.VisitMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final VisitMapper visitMapper ;
    public void save(Account account) {
        accountMapper.save(account);

    }
    public void update(Account account) {
        System.out.println("AccountServiceUpdate:" + account);
        accountMapper.update(account);
        // visit 초기화 및 생성 (default value=0)
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
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
    public boolean countByHomePage(String homepage){
        if(accountMapper.countByHomePage(homepage) >= 1){
            return true;
        }
        return false;
    }

    public String accountCheck(){
        String sessionEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (sessionEmail == null) {
            return null;
        }

        return accountMapper.findByEmail(sessionEmail).getHomepage();
    }

    public List<Account> findAll()
    {
        return accountMapper.findAll();
    }

    public String findNameByAccountId(long accountId){
        return  accountMapper.findNameByAccountId(accountId);
    }

    //마이페이지 댓글리스트
    public String findFileByAccountId(){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return accountMapper.findFileByAccountId(oauth2User.getAccountId());
    }

    public List<Account> findAllOrderByCreatedAt() {
        return accountMapper.findAllOrderByCreatedAt();
    }

    public String findHomepageByAccountId(Long accountId) {
        return accountMapper.findHomepageByAccountId(accountId);
    }


    public Account findByHomepage(String homepage) {
        return accountMapper.findByHomepage(homepage);
    }


    public String findEmailByAccountId(Long accountId) {
        return accountMapper.findEmailByAccountId(accountId);
    }

    public List<Account> findbyhits() {
        return accountMapper.findbyhits();
    }
    public List<Account> findbypost() {
        return accountMapper.findbypost();
    }
}
