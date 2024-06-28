package org.bitcamp.devlog.controller.heart;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Heart;
import org.bitcamp.devlog.service.HeartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartRestController {
    private final HeartService heartService;

    @GetMapping("/heart/count")
    public Long countByPostId(@RequestParam("postId") Long postId) {
        return heartService.countHeartByPostId(postId);
    }

    @PostMapping("/heart/click")
    public void clickHeart(@RequestParam("postId") Long postId) {
        heartService.clickHeart(postId);
    }

    @GetMapping("/heart/list")
    public List<Heart> findByAccountId() {
        return heartService.findByAccountId();
    }
}