package org.bitcamp.devlog.controller.account;

import com.nimbusds.oauth2.sdk.util.OrderedJSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.PostService;
import org.bitcamp.devlog.service.minio.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountRestController {
    private final AccountService accountService;
    private final MinioService minioService;
    private final PostService postService;

    @PostMapping("/blog/create")
    public boolean saveBlog(@RequestParam String blogId,
                         @RequestParam String biography,
                         @RequestParam String homepage,
                         @RequestParam(required = false) MultipartFile file) {

        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Oauth2User.accountId:" + oauth2User.getAccountId());

        Account account = new Account().builder()
                .accountId(oauth2User.getAccountId())
                .blogId(blogId)
                .biography(biography)
                .homepage(homepage)
                .build();
        if(!file.isEmpty()){
            String fileName = minioService.uploadFile("devlog", oauth2User.getName(), file);
            account.setFile((fileName));
        }
        if(accountService.countByHomePage(homepage)){
            return true;
        }
        System.out.println("saveBlog: " + account);
        accountService.update(account);
        return false;
    }

    @GetMapping("/account/check")
    public String account_check(){
        return accountService.accountCheck();
    }
    @PostMapping("/blog/check")
    public Boolean blog_check(@RequestParam String pathName){
        return accountService.countByHomePage(pathName);
    }

    @GetMapping("/new-blog/userdata")
    public ResponseEntity<Map<String, Object>> newblogAccounts(){
        List<Account> accounts =  accountService.findAllOrderByCreatedAt();
        List<Map<String, Object>> newBlogData = new ArrayList<>();

        System.out.println(111111);

        for(Account account : accounts){
            Map<String, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("posts", postService.findAllByAccountIdLong(account.getAccountId()));
            newBlogData.add(map);
        }

        return new ResponseEntity(newBlogData, HttpStatus.OK);
    }

}
