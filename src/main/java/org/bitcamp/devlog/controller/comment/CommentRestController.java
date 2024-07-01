package org.bitcamp.devlog.controller.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Comment;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.AccountService;
import org.bitcamp.devlog.service.CommentService;
import org.bitcamp.devlog.service.PostService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "마이블로그 페이지 댓글", description = "마이블로그 페이지에서 댓글 작성, 삭제 가능한 API Controller")
public class CommentRestController {

    private final CommentService commentService;
    private final AccountService accountService;
    private final PostService postService;

    //마이페이지 댓글리스트
    @GetMapping("/api/mypage/comment")
    @Operation(summary = "마이페이지 댓글리스트", description = "마이페이지 댓글리스트를 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<List<Map<String, Object>>> mypageComments(){

        List<Comment> comments = commentService.findAllByAccountId();
        String file = accountService.findFileByAccountId();
        List<String> homepages = new ArrayList<>();
        List<String> postUrls = new ArrayList<>();
        for(Comment comment : comments){
            Long postId = 0L;
            //나의 홈페이지 주소넣기
            homepages.add(
                accountService.findHomepageByAccountId(
                    postService.findAccountIdByPostId(
                        commentService.findPostIdByCommentId(comment.getCommentId())
                    )
                )
            );
            //postUrl넣기
            postUrls.add(
                postService.findPostUrlByPostId(
                        commentService.findPostIdByCommentId(
                                comment.getCommentId()
                        )
                )
            );

        }
        List<Map<String, Object>> commentData = new ArrayList<>();
        for(int index = 0; index < comments.size(); index++){
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("comment", comments.get(index));
            commentMap.put("homepage", homepages.get(index));
            commentMap.put("postUrl", postUrls.get(index));
            commentMap.put("file", file);
            commentData.add(commentMap);
        }

        return new ResponseEntity<>(commentData, HttpStatus.OK);
    }
    //    작성 댓글 저장
    @PostMapping("/api/comment/write")
    @Operation(summary = "댓글 저장", description = "댓글를 저장하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "405", description = "no information")
    @ApiResponse(responseCode = "403", description = "forbidden")
    @ApiResponse(responseCode = "404", description = "not found")
    @ApiResponse(responseCode = "500", description = "server error")
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

    //마이페이지 댓글리스트
    @PostMapping("/api/comment/list")
    @Operation(summary = "마이페이지 댓글리스트", description = "마이페이지 댓글리스트를 반환하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
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
    @Operation(summary = "마이페이지 댓글 삭제", description = "마이페이지 댓글를 삭제하는 로직")
    @ApiResponse(responseCode = "200", description = "success")
    @ApiResponse(responseCode = "400", description = "bad request")
    @ApiResponse(responseCode = "405", description = "no information")
    @ApiResponse(responseCode = "403", description = "forbidden")
    @ApiResponse(responseCode = "404", description = "not found")
    @ApiResponse(responseCode = "500", description = "server error")
    public ResponseEntity<String> mypageCommentDelete(
            @RequestBody Map<String, Long> commentIdMap
    ) {
        Long commentId = commentIdMap.get("commentId");

        commentService.delete(commentId);
        return new ResponseEntity<>("댓글이 성공적으로 삭제 되었습니다.",HttpStatus.OK);
    }


}