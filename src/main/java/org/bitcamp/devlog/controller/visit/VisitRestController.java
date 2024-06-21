package org.bitcamp.devlog.controller.visit;

import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Oauth2User;
import org.bitcamp.devlog.service.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VisitRestController {

    private final VisitService visitService;

    @GetMapping("/api/mypage/visits")
    public ResponseEntity<Long> mypageHearts() {
        Oauth2User oauth2User = (Oauth2User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        Long visitCount = visitService
            .findVisitCountByAccountId(oauth2User.getAccountId());

        return new ResponseEntity<>(visitCount, HttpStatus.OK);
    }
}
