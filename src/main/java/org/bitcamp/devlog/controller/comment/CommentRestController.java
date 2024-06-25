package org.bitcamp.devlog.controller.comment;

import java.util.ArrayList;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;
    private final AccountService accountService;

    //마이페이지 댓글리스트
    @GetMapping("/api/mypage/comment")
    public ResponseEntity<Map<String, Object>> mypageComments(){

        List<Comment> comments = commentService.findAllByAccountId();
        String file = accountService.findFileByAccountId();

        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("comments", comments);
        commentMap.put("file", file);

        return new ResponseEntity<>(commentMap, HttpStatus.OK);
    }
    //    작성 댓글 저장
    @PostMapping("/api/comment/write")
    public void saveComment(@RequestBody Map<String, Object> commentData) {
//        현재 로그인된 아이디 가져오기
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
//      받은 데이터를  dto에 넣기
        Comment comment = Comment.builder()
                .postId(Long.valueOf(commentData.get("postId").toString()))
                .parentId(Long.valueOf(commentData.get("parentId").toString()))
                .accountId(oauth2User.getAccountId())
                .cContent(commentData.get("cContent").toString())
                .build();
//        저장
        commentService.save(comment);
    }

    @PostMapping("/api/comment/list")
    public ResponseEntity<List<Map<String, Object>>> listComments(@RequestParam Long postId) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Comment> comments = commentService.findAllByPostId(postId);

        for (Comment comment : comments) {
            Map<String, Object> commentData = new HashMap<>();
            commentData.put("comment", comment);
            String name = commentService.findnameByAccountId(comment.getAccountId());
            commentData.put("name", name);
            list.add(commentData);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //마이페이지 댓글 삭제
    @PostMapping("/api/mypage/comment/delete")
    public ResponseEntity<String> mypageCommentDelete(
            @RequestBody Map<String, Long> commentIdMap
    ) {
        Long commentId = commentIdMap.get("commentId");
        System.out.println(commentId);
        commentService.delete(commentId);
        return new ResponseEntity<>("댓글이 성공적으로 삭제 되었습니다.",HttpStatus.OK);
    }


}