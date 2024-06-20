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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

        System.out.println("Received file: " + (file != null ? file.getOriginalFilename() : "No file"));
        System.out.println("Received postData: " + postData);
        System.out.println("User: " + oauth2User);
        System.out.println(oauth2User.getAccountId());

        // 새 Post 객체 생성 및 저장
        Post post = Post.builder()
            .title((String) postData.get("title" ))
            .pContent((String) postData.get("pContent" ))
            .postUrl(String.valueOf(UUID.randomUUID()))
            .openType(Long.parseLong((String) postData.getOrDefault("openType", "0" )))
            .accountId(oauth2User.getAccountId())
            .categoryId(
                categoryService.findCategoryIdByCategoryType(
                    (String) postData.get("categoryType" )))
            .file(minioService.uploadFile("devlog", oauth2User.getEmail(), file))
            .build();

        // post 내용 저장
        postService.save(post);

        // 태그 저장 및 PostTag 연결
        List<String> postTags = (List<String>) postData.get("postTags");
        if (postTags != null) {
            for (String tagName : postTags) {
                Long tagId = tagService.findTagIdByTagName(tagName);
                if (tagId == null) { // 태그 id가 없으면
                    // 태그 저장
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

                // 포스트 태그 저장
                postTagService.save(
                        PostTag.builder()
                                .postId(post.getPostId())
                                .tagId(tagId)
                                .build()
                );
            }
        }

        // 썸네일 ncp에 저장 (파일 처리 로직 추가 필요)

        return ResponseEntity.ok("post를 저장하였습니다.");
    }
}
