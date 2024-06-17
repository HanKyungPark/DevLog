package org.bitcamp.devlog.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Tag;

@Mapper
public interface TagMapper {
    Long findTagIdByTagName(String tagName);
    void save(Tag tag);
}