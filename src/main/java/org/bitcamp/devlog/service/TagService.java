package org.bitcamp.devlog.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.mapper.TagMapper;
import org.springframework.stereotype.Service;

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

    public String findTagNameByTagId(Long tagId){
        return tagMapper.findTagNameByTagId(tagId);
    }

    public List<String> findAllTagNameByTagId(List<Long> tagIds) {
        List<String> tagNames = new ArrayList<>();
        for(Long tagId : tagIds){
            tagNames.add(tagMapper.findTagNameByTagId(tagId));
        }
        return tagNames;
    }
}
