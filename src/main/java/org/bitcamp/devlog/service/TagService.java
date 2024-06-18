package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Tag;
import org.bitcamp.devlog.mapper.TagMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;

    Long findTagIdByTagName(String tagName) {
        Long tagId = tagMapper.findTagIdByTagName(tagName);
        return tagId;
    }

    void save(Tag tag) {
        tagMapper.save(tag);
    }

}
