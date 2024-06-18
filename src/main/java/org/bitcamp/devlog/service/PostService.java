package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Post;
import org.bitcamp.devlog.mapper.CategoryMapper;
import org.bitcamp.devlog.mapper.PostMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final CategoryMapper categoryMapper

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
}
