package org.bitcamp.devlog.controller.account;

import com.nimbusds.oauth2.sdk.util.OrderedJSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Account;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.PostService;
import org.bitcamp.devlog.service.minio.MinioService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "계정 API", description = "Account 테이블에 있는 데이터를 저장 및 조회, 수정, 삭제 가능한 API Controller")
public class AccountRestController {
    private final AccountService accountService;
    private final MinioService minioService;
    private final PostService postService;


    @PostMapping("/blog/create")
    @Operation(summary = "Blog 저장", description = "blogId(블로그 이름), biography(블로그 설명), homepage(홈페이지 Url)를 입력받아 저장하는 로직 ",
        parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "blogId", description = "블로그 이름", required = true, example = "devlog"),
            @Parameter(in = ParameterIn.QUERY, name = "biography", description = "블로그 설명", required = true, example = "devlog"),
            @Parameter(in = ParameterIn.QUERY, name = "homepage", description = "홈페이지 Url", required = true, example = "devlog"),
            @Parameter(in = ParameterIn.QUERY, name = "file", description = "사진", required = false)
        })
    @ApiResponse(responseCode = "200", description = "success")
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
    @Operation(summary = "계정 검색", description = "로그인 되어 있는 지 확인하는 로직")
    @ApiResponse(responseCode = "200", description = "True, False")
    public String account_check(){
        return accountService.accountCheck();
    }
    @PostMapping("/blog/check")
    @Operation(summary = "Blog 확인", description = "homepage를 입력받아 Boolean 반환하는 로직 ",
        parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "pathName", description = "홈페이지 Url", required = true, example = "devlog"),
        })
    @ApiResponse(responseCode = "200", description = "True, False", useReturnTypeSchema = true)
    public Boolean blog_check(@RequestParam String pathName){
        return accountService.countByHomePage(pathName);
    }


    @GetMapping("/new-blog/userdata")
    @Operation(summary = "블로그 조회 API", description = "계정 생성일을 기준으로 블로그 정보를 반환하는 로직 ")
    @ApiResponse(responseCode = "200", description = "success")
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

    @PostMapping("/user/info")
    @Operation(summary = "유저 정보 조회 API", description = "homepage(홈페이지 Url)를 입력 받아 유저 정보를 반환하는 로직",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "homepage", description = "홈페이지 URL", required = true, example = "devlog")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public Account findByHomepage(@RequestParam String homepage){


     return accountService.findByHomepage(homepage);
    }

    @PostMapping("/post/rankbyhits")
    @Operation(summary = "계정 리스트 조회(조회수) API", description = "계정 리스트를 조회수 기준으로 정렬하여 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    public List<Account> rankbyHits(){
        return accountService.findbyhits();
    }
    @PostMapping("/post/rankbyposts")
    @Operation(summary = "계정 리스트 조회(포스팅) API", description = "계정 리스트를 포스팅 수 기준으로 정렬하여 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    public List<Account> rankbyPosts(){
        return accountService.findbypost();
    }
}