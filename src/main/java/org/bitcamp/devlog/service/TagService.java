package org.bitcamp.devlog.service;

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

}
