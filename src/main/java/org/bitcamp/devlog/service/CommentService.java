package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Comment;
import org.bitcamp.devlog.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    //댓글 추가
    void save(Comment comment) {
        commentMapper.save(comment);
    }

    //댓글 수정
    void update(Comment comment) {
        commentMapper.update(comment);
    }

    //댓글삭제
    void delete(Long commentId) {
        commentMapper.delete(commentId);
    }

    //댓글조회
    List<Comment> findAllByPostId(Long postId) {
        List<Comment> comments = commentMapper
            .findAllByPostId(postId);
        return comments;
    }

}
