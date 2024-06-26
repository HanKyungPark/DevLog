package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.PostTag;
import org.bitcamp.devlog.mapper.PostTagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTagService {

    private final PostTagMapper postTagMapper;

    //태그추가
    public void save(PostTag postTag) {
        postTagMapper.save(postTag);
    }

    //태그삭제
    public void delete(Long postTagId) {
        postTagMapper.delete(postTagId);
    }

    //태그검색
    public List<Long> findAllPostIdByTagId(Long tagId) {
        return postTagMapper.findAllPostIdByTagId(tagId);
    }

    public PostTag findPostTagId(Long postTagId) {
        return postTagMapper.findPostTagId(postTagId);
    }

    public List<Long> findAllTagIdByPostId (Long postId){
        return postTagMapper.findAllTagIdByPostId(postId);
    }

    public PostTag findByPostIdAndTagId(Long tagId, Long postId) {
        return postTagMapper.findByPostIdAndTagId(tagId, postId);
    }

    public void deleteAllByPostId(Long postId) {
        postTagMapper.deleteAllByPostId(postId);
    }
}
