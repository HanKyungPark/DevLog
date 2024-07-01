package org.bitcamp.devlog.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.mapper.*;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CategoryMapper categoryMapper;
    private final CommentMapper commentMapper;
    private final PostTagService postTagService;
    private final TagService tagService;
    private final PostTagMapper postTagMapper;
    private final TagMapper tagMapper;
    private final AccountMapper accountMapper;


    public void save(Post post){
        postMapper.save(post);

    }

    public void update(Post post){
        postMapper.update(post);
    }

    public void delete(Post post){
        postMapper.delete(post);
    }

    public Map<String, Object> findById(Post post){
        Map<String, Object> map = new HashMap<>();


        map.put("post",postMapper.findById(post));
        map.put("category",categoryMapper.findByPostId(post));

        return map;
    }

    public Post findByPostIdAndAccountId(Long postId, Long accountId){
        return postMapper.findByPostIdAndAccountId(postId, accountId);
    }

    public List<Map<String,Object>> findRandomPosts() {
        return postMapper.findRandomPosts();
    }


    public List<Post> findAllByAccountId(){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        return postMapper.findAllByAccountId(oauth2User.getAccountId());
    }

    public List<Map<String, Object>> findByHomePage(String homepage){
        return postMapper.findByHomePage(homepage);
    }


    public List<Object> findAllbypostUrl(String postUrl){

        List<Object> list=postMapper.findBypostUrl(postUrl);
        return list;
    }


    public void deleteByPostUrl(String postUrl) {
        postMapper.deleteByPostUrl(postUrl);
    }

    public Post findByPostUrl(String postUrl) {
        return postMapper.findByPostUrl(postUrl);
    }

    public Long findPostIdByPostUrl(String postUrl) {
        return postMapper.findPostIdByPostUrl(postUrl);
    }

    public List<Long> findAllTagIdByPostId(Long postId) {
        return postMapper.findAllTagIdByPostId(postId);
    }

    public List<Post> findAllByAccountIdLong(Long accountId){
        return postMapper.findAllByAccountIdLong(accountId);
    }


    public Long findAccountIdByPostId(Long postId) {
        return postMapper.findAccountIdByPostId(postId);
    }

    public void deleteByPostId(Long postId) {
        postMapper.deleteByPostId(postId);
    }

    public String findPostUrlByPostId(Long postId) {
        return postMapper.findPostUrlByPostId(postId);
    }


    public void updateHitsByPostid ( Long postId){postMapper.updateHitsByPostid(postId);};

    public Long findHitsByPostid (Long accountId){return postMapper.findHitsByPostid(accountId);};

    public List<Map<String, Object>> findByCategoryIdAndAccountId( Long accountId, Long categoryId) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId",accountId);
        map.put("categoryId",categoryId);
        return postMapper.findByCategoryIdAndAccountId(map);
    }
    public Long findHitsByHomepage(String homepage) {
        return postMapper.findHitsByHomepage(homepage);
    }


    public List<Map<String, Object>> findAllByAccountIdOpenOnly(Long pageAccountId) {
        return postMapper.findAllByAccountIdOpenOnly(pageAccountId);
    }

    public List<Post> findAllByCategoryId(Long categoryId) {
        return postMapper.findAllByCategoryId(categoryId);
    }
}
