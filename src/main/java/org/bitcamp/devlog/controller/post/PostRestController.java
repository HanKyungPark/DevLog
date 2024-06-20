package org.bitcamp.devlog.controller.post;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.service.CategoryService;
import org.bitcamp.devlog.service.PostService;
import org.bitcamp.devlog.service.PostTagService;
import org.bitcamp.devlog.service.TagService;
import org.bitcamp.devlog.service.minio.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final CategoryService categoryService;
    private final MinioService minioService;

    @PostMapping(value = "/api/post/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> posting(
            @RequestPart("postData") Map<String, Object> postData,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        /*나중에 서비스로 빼줘서 트랜잭션 하기*/

        // accountId 생성
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        /*해야할 일*/

        //포스트 저장 void createPost
        Post post = Post.builder()
            .title((String) postData.get("title" ))
            .pContent((String) postData.get("pContent" ))
            .postUrl(String.valueOf(UUID.randomUUID()))
            .openType(Long.parseLong((String) postData.getOrDefault("openType", "0" )))
            .accountId(oauth2User.getAccountId())
            .categoryId(
                categoryService.findCategoryIdByCategoryType(
                    (String) postData.get("categoryType" )))
            .file(oauth2User.getEmail()+"/"+minioService.uploadFile("devlog", oauth2User.getEmail(), file))
            .build();

        // post 내용 저장
        postService.save(post);

        /**
         *
         * 태그이름 확인후 저장
         * 매개변수: postId
         */
        List<String> postTags = (List<String>) postData.get("postTags");
        if (postTags != null) {
            for (String tagName : postTags) {
                Long tagId = tagService.findTagIdByTagName(tagName);
                if (tagId == null) {
                    tagService.save(
                            Tag.builder()
                                    .tagName(tagName)
                                    .build()
                    );
                    tagId = tagService.findTagIdByTagName(tagName);
                    if (tagId == null) {
                        throw new NullPointerException("tag이름이 제대로 저장되지 못했습니다.");
                    }
                }

                postTagService.save(
                        PostTag.builder()
                                .postId(post.getPostId())
                                .tagId(tagId)
                                .build()
                );
            }
        }

        return ResponseEntity.ok("post를 저장하였습니다.");
    }

    /** 게시글 조회
     * RQ : post_id, account_id
     * RP :
     */

    @GetMapping("/api/post/list")
    public ResponseEntity<List<Post>> feedPagePostList() {
        List<Post> posts = postService.findRandomPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }



}
