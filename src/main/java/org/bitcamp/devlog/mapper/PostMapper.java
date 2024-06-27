package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Post;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
    // 작성
    void save(Post post);

    //수정
    void update(Post post);

    //삭제
    void delete(Post post);

    //조회
    Post findById(Post post);

    //좋아용
    void CountByHeart(Long postId);

    //게시글 리스트
    List<Post> findAllByAccountId(Long accountId);

    //태그검색
    Post findByPostIdAndAccountId(Long postId, Long accountId);

    //제목검색
    List<Post> findAllByTitleAndAccountId(String title, Long accountId);

    //랜덤으로 리스트
    List<Map<String,Object>> findRandomPosts();

    //마이블로그
    List<Post> findByHomePage(String homepage);

    //posturl로 글 디테일 가져오기
    List<Object> findBypostUrl(String postUrl);

    //마이페이지 포스트 삭제
    void deleteByPostUrl(String postUrl);

    Post findByPostUrl(String postUrl);

    Long findPostIdByPostUrl(String postUrl);

    List<Long> findAllTagIdByPostId(Long postId);

    List<Post> findAllByAccountIdLong(Long accountId);

    Long findAccountIdByPostId(Long postId);

    void deleteByPostId(Long postId);

    String findPostUrlByPostId(Long postId);

    Post findByCategoryIdAndAccountId(Long categoryId, Long accountId);
}
