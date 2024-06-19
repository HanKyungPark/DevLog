package org.bitcamp.devlog.controller.account;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.minio.MinioService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountRestController {
    private final AccountService accountService;
    private final MinioService minioService;

    @PostMapping("/blog/create")
    public void saveBlog(@RequestParam String blogId,
                         @RequestParam String biography,
                         @RequestParam String homepage,
                         @RequestParam MultipartFile file) {

        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String fileName = minioService.uploadFile("devlog", oauth2User.getName(), file);

        Account account = new Account().builder()
                .blogId(blogId)
                .biography(biography)
                .homepage(homepage)
                .build();
        accountService.update(account);
    }
}
