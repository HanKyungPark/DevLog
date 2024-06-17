package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.PostTag;

import java.util.List;

@Mapper
public interface PostTagMapper {
    void save(PostTag postTag);
    void delete(Long postTagId);
    List<Long> findAllPostIdByTagId(Long tagId);
}
