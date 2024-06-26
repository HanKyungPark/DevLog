package org.bitcamp.devlog.controller.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.service.*;
import org.bitcamp.devlog.service.minio.MinioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final CategoryService categoryService;
    private final MinioService minioService;
    private final VisitService visitService;
    private final AccountService accountService;


    @PostMapping(value = "/api/post/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> posting(
            @RequestPart("postData") Map<String, Object> postData,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {

        // accountId 생성
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        System.out.println(postData);
        List<String> categoryType = (List)postData.get("category");
        Long categoryId = categoryService.findCategoryIdByCategoryType(categoryType.get(0));
        //포스트 저장 void createPost
        Post post = Post.builder()
                .title((String) postData.get("title"))
                .pContent((String) postData.get("pContent"))
                .postUrl(String.valueOf(UUID.randomUUID()))
                .openType(Long.parseLong((String) postData.getOrDefault("openType", "0")))
                .accountId(oauth2User.getAccountId())
                .categoryId(categoryId)
                .file("https://minio.bmops.kro.kr/devlog/" + oauth2User.getEmail() + "/"
                        + minioService.uploadFile("devlog", oauth2User.getEmail(), file))
                .build();

        // post 내용 저장
        postService.save(post);

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

    @PostMapping("/api/post/list")
    public ResponseEntity<List<Map<String, Object>>> feedPagePostList() {
        List<Map<String, Object>> posts = postService.findRandomPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PostMapping("/api/post/myblog/list")
    public List<Map<String, Object>> findByHomePage(@RequestParam String homepage) {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Long pageAccountId = accountService.findByHomepage(homepage).getAccountId();
        Long myAccountId = oauth2User.getAccountId();

        List<Map<String, Object>> posts = new ArrayList<>();

        if(pageAccountId == myAccountId){
            posts = postService.findByHomePage(homepage);
        } else {
            posts = postService.findAllByAccountIdOpenOnly(pageAccountId);
        }

        return posts;
    }

    @PostMapping("/api/post/test")
    public List<Map<String,Object>> test(@RequestParam String homepage) {
        return postService.findByHomePage(homepage);
    }

    @PostMapping("/detail/comment")
    public ResponseEntity<List<String>> commentPost(@RequestParam String postId ) {
        List<String> posts = new ArrayList<>();
        posts.add(postId);
        System.out.println(posts.get(0));
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //마이페이지 post리스트
    @GetMapping("/api/mypage/posts")
    public ResponseEntity<Map<String, Object>> myPagePosts() {
        Map<String, Object> postMap = new HashMap<>();
        List<Post> posts = postService.findAllByAccountId();
        String homepage = "";
        if (posts.size() > 0) {
            homepage = accountService
                    .findHomepageByAccountId(
                            posts.get(0).getAccountId()
                    );
        }
        postMap.put("posts", posts);
        postMap.put("homepage", homepage);

        return new ResponseEntity<>(postMap, HttpStatus.OK);
    }

    //마이페이지 포스트 삭제
    @PostMapping("/api/mypage/post/delete")
    public ResponseEntity<String> myPagePostDelete(
            @RequestBody Map<String, Long> postId
    ) {
        postService.deleteByPostId(postId.get("postId"));
        return new ResponseEntity<>("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }


    //마이페이지 -> 게시글수정페이지 데이터 요청
    @PostMapping("api/post/mypage/update")
    public ResponseEntity<Map<String, Object>> myPagePostUpdate(
            @RequestBody Map<String, String> requestPostUrl
    ) {

        Map<String, Object> postUpdateData = new HashMap<>();
        String postUrl = requestPostUrl.get("postUrl");

        Post post = postService.findByPostUrl(postUrl);
        String homepage = accountService.findHomepageByAccountId(post.getAccountId());
        postUpdateData.put("homepage", homepage);
        //게시글 내용
        if (post != null) {
            postUpdateData.put("post", post);
        } else {
            throw new NullPointerException("url에 해당하는 Post 데이터가 없습니다.");
        }

        List<String> tags =
                tagService.findAllTagNameByTagId(
                        postTagService.findAllTagIdByPostId(
                                postService.findPostIdByPostUrl(
                                        postUrl
                                )
                        )
                );

        if (tags.size() != 0) {
            postUpdateData.put("tags", tags);
        }

        if (post.getCategoryId() != null) {
            String categoryType =
                    categoryService
                            .findCategoryTypeByCategoryId(post.getCategoryId());
            postUpdateData.put("categoryType", categoryType);
        }

        return new ResponseEntity<>(postUpdateData, HttpStatus.OK);
    }

    @PostMapping("/api/post/detail")
    public ResponseEntity<List<Object>> detailPost(@RequestParam String info
    ) {

        List<Object> list = postService.findAllbypostUrl(info);
        System.out.println(list.size());

        postService.updateHitsByPostid(postService.findPostIdByPostUrl(info));

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/api/mypage/visits")
    public Long countByVisit() {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return postService.findHitsByPostid(oauth2User.getAccountId());
    }

    @PostMapping("/api/search/category")
    public List <Map<String, Object>> findByCategoryIdAndAccountId(@RequestParam Long accountId,
                                                                   @RequestParam Long categoryId) {
        return postService.findByCategoryIdAndAccountId(accountId, categoryId);
    }

    //마이포스트 수정사항 update
    @PostMapping("/api/post/mypage/update-post")
    public ResponseEntity<String> mypageUpdatePost(
            @RequestPart("updateData") Map<String, Object> updateData,
            @RequestPart(value = "file", required = false) MultipartFile file
    ){
        System.out.println(updateData);
        System.out.println(file);
        System.out.println(updateData.get("category"));
        List<String> category = (List)updateData.get("category");
        Post updatePost = postService.findByPostUrl((String)updateData.get("postUrl"));
        if(category.size() > 0){
            Long categoryId = categoryService.findCategoryIdByCategoryType(category.get(0).toString());
            updatePost.setCategoryId(categoryId);
        }
        List<String> tags = (List<String>) updateData.get("updateTags");
        String email = accountService.findEmailByAccountId(updatePost.getAccountId());


        //해당 포스트의 제목, 내용, 공개여부, 사진을 바꾼다
        updatePost.setTitle((String)updateData.get("title"));
        updatePost.setPContent((String)updateData.get("pContent"));
        updatePost.setOpenType(Long.parseLong((String)updateData.get("openType")));
        if(file != null){
            updatePost.setFile("https://minio.bmops.kro.kr/devlog/" + email + "/"
                    + minioService.uploadFile("devlog", email, file));
        }

        postService.update(updatePost);

        //태그 삭제
        postTagService.deleteAllByPostId(updatePost.getPostId());

        //태그 저장
        if(tags != null){
            for(String tag : tags){
                Long tagId = tagService.findTagIdByTagName(tag);
                if(tagId == null){
                    tagService.save(Tag.builder()
                            .tagName(tag)
                            .build());
                    tagId = tagService.findTagIdByTagName(tag);
                }
                postTagService.save(PostTag.builder()
                        .postId(updatePost.getPostId())
                        .tagId(tagId)
                        .build()
                );
            }
        }

        return new ResponseEntity<>("성공적으로 수정하였습니다.", HttpStatus.OK);
    }

    @PostMapping("/api/post/view")
    public long findHitsByHomepage(@RequestParam String homepage) {
        return postService.findHitsByHomepage(homepage);

    }

}