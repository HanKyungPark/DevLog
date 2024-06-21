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
    public void save(Comment comment) {
        commentMapper.save(comment);
    }

    //댓글 수정
    public void update(Comment comment) {
        commentMapper.update(comment);
    }

    //댓글삭제
    public void delete(Long commentId) {
        commentMapper.delete(commentId);
    }

    //댓글조회
    public List<Comment> findAllByPostId(Long postId) {
        List<Comment> comments = commentMapper
            .findAllByPostId(postId);
        return comments;
    }


    public List<Comment> findAllByAccountId(Long accountId) {
        List<Comment> comments = commentMapper
            .findAllByAccountId(accountId);
        return comments;
    }

}
