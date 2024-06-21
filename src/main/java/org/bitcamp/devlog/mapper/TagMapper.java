package org.bitcamp.devlog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Tag;

import java.util.List;

@Mapper
public interface TagMapper {

    //태그검색, 태그추가
    Long findTagIdByTagName(String tagName);

    //태그추가
    void save(Tag tag);

    String findTagNameByTag_id(Long tagId);
}

