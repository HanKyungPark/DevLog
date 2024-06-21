package org.bitcamp.devlog.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.mapper.CategoryMapper;
import org.bitcamp.devlog.mapper.PostMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.bitcamp.devlog.mapper.PostTagMapper;
import org.bitcamp.devlog.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CategoryMapper categoryMapper;
    private final PostTagService postTagService;
    private final TagService tagService;
    private final PostTagMapper postTagMapper;
    private final TagMapper tagMapper;

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

    public List<Post> findRandomPosts() {
        return postMapper.findRandomPosts();
    }

    public List<Post> findAllByAccountId(){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return postMapper.findAllByAccountId(oauth2User.getAccountId());
    }

    public List<Post> findByHomePage(String homepage){

        return postMapper.findByHomePage(homepage);
    }

    public List<Object> findPost_namebypostUrl(String postUrl){
//        List<Long> postTagid=new ArrayList<>();
//        List<String> postName=new ArrayList<>();
//        Long post_id=1L;
//        List<Post> posts = findAllPosts(postUrl);//posts에는 postdetail들이 들어있음
//        //postdetail 에서 post_id만을 꺼내서 post_id에 담는다.
//        post_id=(posts.get(0).getPostId());
//        System.out.println("post_id"+post_id);//통과
//        //post_id를 통해서 post_tag의 id 값을 담아온다.
//        postTagid = postTagMapper.findPostTagIdByPostId(post_id);
//        System.out.println("postTagid.size()"+postTagid.size());
//        for(int i=0;i<postTagid.size();i++){
//            postName.add(tagMapper.findTagNameByTag_id(postTagid.get(i)));
//        }
//        System.out.println("postName.size()"+postName.size());
//        return postName;
        List<Object> list=postMapper.findBypostUrl(postUrl);
        return list;
    }
}
