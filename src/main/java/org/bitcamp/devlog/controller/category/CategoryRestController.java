package org.bitcamp.devlog.controller.category;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Category;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.CategoryService;
import org.bitcamp.devlog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리 API", description = "Category 테이블에 데이터를 저장 및 조회, 수정, 삭제를 할 수 있는 API Controller")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final AccountService accountService;
    private final PostService postService;

    //post로 보내는 카테고리
    @GetMapping("/postingForm")
    @Operation(summary = "카테고리 리스트 조회 API", description = "로그인이 되어 있는 계정의 카테고리 리스트를 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    public Map<String, Object> categories() {
        Map<String, Object> map = new HashMap<>();
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        List<Category> categories = categoryService.findAllByAccountId(oauth2User.getAccountId());


        if (categories.isEmpty()) {
            map.put("code", HttpStatus.BAD_REQUEST.value());//400error
            map.put("message", "카테고리 리스트가 없습니다.");
            return map;
        }

        map.put("code", HttpStatus.OK.value());
        map.put("message", "카테고리 리스트입니다.");
        map.put("categories", categories);

        return map;
    }



    //mypage 카테고리추가
    @PostMapping("/api/mypage/category-post")
    @Operation(summary = "카테고리 추가 API", description = "Map 타입을 카테고리 이름을 입력받아 카테고리 테이블 저장하는 로직",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "categoryName", description = "카테고리 이름", required = true, example = "여행")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<Category> mypageSaveCategory(
            @RequestBody Map<String, String> categoryName
    ) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        String categoryType = categoryName.get("categoryName");
        Category category = categoryService.findByCategoryType(categoryType);

        if(category != null){
            return new ResponseEntity<>(new Category(), HttpStatus.OK);
        } else {
            category = Category.builder()
                    .categoryType(categoryName.get("categoryName"))
                    .accountId(oauth2User.getAccountId())
                    .build();

            categoryService.save(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    //마이페이지 카테고리 리스트
    @PostMapping("/api/mypage/categorie")
    @Operation(summary = "카테고리 조회 API", description = "블로그 URL를 입력받아 카테고리 리스트를 반환하는 로직",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "homepage", description = "블로그 URL", required = true, example = "devlog")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<Category>> mypageCategorie(@RequestParam String homepage){
            Long accountId=accountService.findByHomepage(homepage).getAccountId();

        List<Category> categories = categoryService
                .findAllByAccountId(accountId);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    //마이페이지 카테고리 리스트
    @GetMapping("/api/mypage/categories")
    @Operation(summary = "카테고리 조회 API", description = "로그인이 되어있는 계정의 카테고리 리스트를 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<Category>> mypageCategories(){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long accountId = oauth2User.getAccountId();

        List<Category> categories = categoryService
                .findAllByAccountId(accountId);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //카테고리 삭제
    @PostMapping("/api/mypage/category/delete")
    @Operation(summary = "카테고리 삭제 API", description = "카테고리 ID를 입력받아 카테고리를 삭제하는 로직",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "categoryId", description = "카테고리 ID", required = true, example = "1")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<String> mypageCategoryDelete(
            @RequestBody Map<String, Long> categoryIdData
    ){
        Long categoryId = categoryIdData.get("categoryId");
        Category category = categoryService.findByCategoryId(categoryIdData.get("categoryId"));
        List<Post> posts = postService.findAllByCategoryId(categoryId);
        for(Post post : posts){
            post.setCategoryId(null);
            postService.update(post);
        }
        categoryService.delete(category);
        return new ResponseEntity<>("카테고리가 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    //카테고리 수정
    @PostMapping("/api/mypage/category/update")
    @Operation(summary = "카테고리 변경 API", description = "카테고리 ID 받아 카테고리 이름를 변경하는 로직",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "categoryId", description = "카테고리 ID", required = true, example = "1"),
                    @Parameter(in = ParameterIn.QUERY, name = "categoryName", description = "카테고리 이름", required = true, example = "여행")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<String> mypageCategoryUpdate(
            @RequestBody Map<String, String> categoryUpdateData
    ){
        String categoryType = categoryUpdateData.get("categoryType");
        Long categoryId = Long.valueOf(categoryUpdateData.get("categoryId"));
        Category category = categoryService.findByCategoryType(categoryType);
        if(category != null){
            return new ResponseEntity<>("600", HttpStatus.OK);
        } else {
            category = Category.builder()
                    .categoryType(categoryType)
                    .categoryId(categoryId)
                    .build();

            categoryService.update(category);

            return new ResponseEntity<>("카테고리가 성공적으로 삭제되었습니다.", HttpStatus.OK);
        }
    }

}