package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //카테고리 추가
    void save(Category dto);

    //카테고리 수정
    void update(Category dto);

    //카테고리 리스트
    List<Category> findAllByPostId(Long postId);
    void delete (Long categoryId);

    Category findByPostId(Long postId);

}
