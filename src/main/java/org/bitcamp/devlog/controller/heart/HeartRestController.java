package org.bitcamp.devlog.controller.heart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bitcamp.devlog.dto.Heart;
import org.bitcamp.devlog.service.HeartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "좋아요 API", description = "Heart 테이블에 해당하는 데이터 조회, 수정, 추가 ,삭제 API Controller")
public class HeartRestController {
    private final HeartService heartService;

    @GetMapping("/heart/count")
    @Operation(summary = "", description = "",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "", description = "", required = true, example = "devlog")
            })
    @ApiResponse(responseCode = "200", description = "success")
    public Long countByPostId(@RequestParam("postId") Long postId) {
        return heartService.countHeartByPostId(postId);
    }

    @PostMapping("/heart/click")
    @ResponseStatus(HttpStatus.OK)
    public void clickHeart(
        @RequestParam("postId") Long postId
    ) {
        heartService.clickHeart(postId);
    }

    @GetMapping("/heart/list")
    public List<Heart> findByAccountId() {
        List<Heart> hearts = heartService.findByAccountId();
        System.out.println(hearts);
        return hearts;
    }
}