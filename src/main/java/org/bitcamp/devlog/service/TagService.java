package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;



    //태그검색
    public Long findTagIdByTagName(String tagName) {
        return tagMapper.findTagIdByTagName(tagName);
    }


    //태그추가
    public void save(Tag tag) {
        tagMapper.save(tag);
    }


    public String findTagNameByTag_id(Long postId){
        String tagname=tagMapper.findTagNameByTag_id(postId);
        return tagname;
    }
}
