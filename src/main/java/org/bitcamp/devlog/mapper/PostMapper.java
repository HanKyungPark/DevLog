package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Post;

import java.util.List;

@Mapper
public interface PostMapper {
    // 작성
    void save(Post post);

    //수정
    void update(Post post);

    //삭제
    void delete(Post post);

    //조회
    Post findById(Long postId);

    //좋아용
    void CountByHeart(Long postId);

    //게시글 리스트
    List<Post> findAll(Long AccountId);
}
