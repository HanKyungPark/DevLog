package org.bitcamp.devlog.controller.category;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Category;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;
    private final AccountService accountService;

    //post로 보내는 카테고리
    @GetMapping("/postingForm")
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
    @GetMapping("/api/mypage/categories")
    public ResponseEntity<List<Category>> mypageCategories(@RequestParam String homepage){
        Long accountId=accountService.findByHomepage(homepage).getAccountId();

        List<Category> categories = categoryService
            .findAllByAccountId(accountId);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //카테고리 삭제
    @PostMapping("/api/mypage/category/delete")
    public ResponseEntity<String> mypageCategoryDelete(
        @RequestBody Map<String, Long> categoryId
    ){
        Category category = categoryService.findByCategoryId(categoryId.get("categoryId"));
        categoryService.delete(category);
        return new ResponseEntity<>("카테고리가 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    //카테고리 수정
    @PostMapping("/api/mypage/category/update")
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
