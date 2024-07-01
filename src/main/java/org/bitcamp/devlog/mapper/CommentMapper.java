package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Comment;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    void save(Comment comment);
    void update(Comment comment);
    void delete(Long commentId);
    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByAccountId(Long accountId);

    Long findPostIdByCommentId(Long commentId);

    Comment findByCommentId(Long commentId);
    Long countByPostIdAndHomepage(Map<String, Object> map);

}
