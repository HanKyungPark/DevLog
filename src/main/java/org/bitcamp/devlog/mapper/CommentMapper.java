package org.bitcamp.devlog.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.bitcamp.devlog.dto.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    void save(Comment comment);
    void update(Comment comment);
    void delete(Long commentId);
    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByAccountId(Long accountId);

<<<<<<< HEAD
=======
    Long findPostIdByCommentId(Long commentId);
>>>>>>> b6c0351 (mypage link 지정)
}
