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
    void save(PostTag postTag) {
        postTagMapper.save(postTag);
    }

    //태그삭제
    void delete(Long postTagId) {
        postTagMapper.delete(postTagId);
    }

    //태그검색
    List<Long> findAllPostIdByTagId(Long tagId) {
        List<Long> postIds = postTagMapper.findAllPostIdByTagId(tagId);
        return postIds;
    }

}
