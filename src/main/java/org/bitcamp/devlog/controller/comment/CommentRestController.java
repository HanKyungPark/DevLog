package org.bitcamp.devlog.controller.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Comment;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;
    private final AccountService accountService;

    @GetMapping("/api/mypage/comment")
    public ResponseEntity<Map<String, Object>> mypageComments(){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        Long accountId =oauth2User.getAccountId();

        List<Comment> comments = commentService.findAllByAccountId(accountId);
//        String file = accountService.findFileByAccountId(accountId);

        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("comments", comments);
//        commentMap.put("file", file);

        return new ResponseEntity<>(commentMap, HttpStatus.OK);
    }

}
