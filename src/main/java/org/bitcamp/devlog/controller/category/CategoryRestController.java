package org.bitcamp.devlog.controller.category;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Category;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    //post로 보내는 카테고리
    @GetMapping("/postingForm")
    public Map<String, Object> categories() {
        Map<String, Object> map = new HashMap<>();
//        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        List<Category> categories = categoryService.findAllByAccountId(oauth2User.getAccountId());




        List<Category> categories = new ArrayList<>();
        categories.add(Category.builder()
                        .categoryId(1L)
                        .accountId(1L)
                .build());

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

}

//package org.bitcamp.devlog.controller.category;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.bitcamp.devlog.dto.Category;
//import org.bitcamp.devlog.dto.Oauth2User;
//import org.bitcamp.devlog.service.CategoryService;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class CategoryRestController {
//
//    private final CategoryService categoryService;
//
//    @GetMapping("/postingForm")
//    public Map<String, Object> categories() {
//        Map<String, Object> map = new HashMap<>();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//
//            if (principal instanceof Oauth2User) {
//                Oauth2User oauth2User = (Oauth2User) principal;
//                List<Category> categories = categoryService.findAllByAccountId(oauth2User.getAccountId());
//
//                if (categories.isEmpty()) {
//                    map.put("code", HttpStatus.BAD_REQUEST.value());
//                    map.put("message", "카테고리 리스트가 없습니다.");
//                } else {
//                    map.put("code", HttpStatus.OK.value());
//                    map.put("message", "카테고리 리스트입니다.");
//                    map.put("categories", categories);
//                }
//            } else if (principal instanceof DefaultOAuth2User) {
//                DefaultOAuth2User oauth2User = (DefaultOAuth2User) principal;
//                String accountId =oauth2User.getAttribute("accountId");
//                List<Category> categories = categoryService.findAllByAccountId(Long.parseLong(accountId));
//
//                if (categories.isEmpty()) {
//                    map.put("code", HttpStatus.BAD_REQUEST.value());
//                    map.put("message", "카테고리 리스트가 없습니다.");
//                } else {
//                    map.put("code", HttpStatus.OK.value());
//                    map.put("message", "카테고리 리스트입니다.");
//                    map.put("categories", categories);
//                }
//            } else {
//                map.put("code", HttpStatus.BAD_REQUEST.value());
//                map.put("message", "인증 정보가 유효하지 않습니다.");
//            }
//        } else {
//            map.put("code", HttpStatus.UNAUTHORIZED.value());
//            map.put("message", "인증되지 않은 사용자입니다.");
//        }
//
//        return map;
//    }
//}