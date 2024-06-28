package org.bitcamp.devlog.service;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Heart;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.mapper.HeartMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HeartService {
    private final HeartMapper heartMapper;

    //좋아요 조회
    public Long countHeartByPostId(Long postId) {
        return heartMapper.countHeartByPostId(postId);
    }

    //계정 좋아요 조회
    public Long countHeartByAccountId(Long accountId) {
        return heartMapper.countHeartByAccountId(accountId);
    }

    // 좋아요 조회
    public void clickHeart(Long postId){
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = oauth2User.getAccountId();
        if(countHeartByAccountId(accountId) == 0){
            heartMapper.save(new Heart().builder().accountId(accountId).postId(postId).build());
        } else if(!(countHeartByAccountId(accountId) == 0)){
            heartMapper.delete(accountId);
        }
    }
}