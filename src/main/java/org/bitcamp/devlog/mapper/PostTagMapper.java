package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.PostTag;

import java.util.List;

@Mapper
public interface PostTagMapper {
    //태그추가
    void save(PostTag postTag);

    //태그삭제
    void delete(Long postTagId);

    //태그검색
    List<Long> findAllPostIdByTagId(Long tagId);

    PostTag findPostTagId(Long postTagId);
}
