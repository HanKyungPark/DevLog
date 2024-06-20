package org.bitcamp.devlog.controller.post;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.service.PostService;
import org.bitcamp.devlog.service.PostTagService;
import org.bitcamp.devlog.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final TagService tagService;
    private final PostTagService postTagService;

    /** 포스트 작성
     * 제목, 내용은 태그에 넣기
     * 사진은 어떤 형태로 오조???
     * 태그는 태그 이름을 조회한다
     * null이면 tag 테이블에 태그를 추가해준다, post_tag 테이블에 tag_id와 post_id로 저장한다
     * post_tag 테이블에
     *
     *
     * @param postData
     * @return
     */
    @PostMapping("/api/post/posting")
    public ResponseEntity<String> posting(
            @RequestBody Map<String, Object> postData,
            @RequestParam(required = false) MultipartFile file
            ) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                    .getPrincipal();
        System.out.println(oauth2User);
        //태그저장
        System.out.println(postData.toString());
        for(String tagName : (List<String>)postData.get("postTags")){
            Long tagId = tagService.findTagIdByTagName(tagName);
            if(tagId == null){ //태그id가 없으면
                //태그저장
                tagService.save(
                    Tag.builder()
                        .tagName(tagName)
                        .build()
                );
            }
            tagId = tagService.findTagIdByTagName(tagName);
            if(tagId == null) {
                throw new NullPointerException("tag이름이 제대로 저장되지 못했습니다.");
            }
            // 포스트 태그 저장
            postTagService.save(
                PostTag.builder()
                    .postId(
                        Long.parseLong(
                            (String)postData.get("postId")
                        ))
                    .tagId(tagId)
                    .build()
            );
        }

        //썸네일 ncp에 저장

        Post post = Post.builder()
            .title((String)postData.get("title"))
            .pContent((String)postData.get("content"))
            .openType(Long.parseLong((String)postData.get("openType")))
            .postUrl((String)postData.get("postUrl"))
            .accountId(oauth2User.getAccountId())
            .build();

        //post내용저장
        postService.save(post);
        if(postService.findByPostIdAndAccountId(post.getPostId(), oauth2User.getAccountId()) == null){
            throw new NullPointerException("Post가 저장되지 못하였습니다.");
        }


        return ResponseEntity.ok("post를 저장하였습니다.");
    }


}
