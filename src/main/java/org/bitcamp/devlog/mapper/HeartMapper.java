package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Heart;

import java.util.List;
import java.util.Map;

@Mapper
public interface HeartMapper {
    // 좋아요 수 조회
    Long countHeartByPostId(Long postId);
    // 계정 좋아요 조회
    Long countHeartByAccountId(Map<String, Object> map);

    // 종아요 조회
    List<Heart> findByAccountId(Long accountId);

    // 좋아요 등록
    void save (Heart heart);

    // 좋아요 삭제
    void deleteByAccountId(Map<String, Object> map);
}
