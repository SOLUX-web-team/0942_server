package smwu._back.post.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smwu._back.post.domain.PostDto;
import smwu._back.post.domain.Scrap;
import smwu._back.post.service.ScrapService;
import smwu._back.users.domain.UserInfoVO;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ScrapController {

    private final ScrapService scrapService;

    @ResponseBody
    @PostMapping("/main/post/{postId}/scrap")
    public Integer clickScrap(@PathVariable long postId, @RequestBody Map<Object, String> userInfo) {
        // 스크랩 버튼 클릭 시
        String userId = userInfo.get("USER_ID");
        System.out.println("스크랩 " + userId);
        return scrapService.saveScrap(userId, postId);
    }

    @ResponseBody
    @PostMapping("/main/post/{postId}/scrapped")
    public Integer isScrapped(@PathVariable long postId, @RequestBody Map<Object, String> userInfo) {
        // 현재 로그인한 유저가 포스트를 스크랩했는가?
        System.out.println("스크랩 여부");
        String userId = userInfo.get("USER_ID");
        return scrapService.isScrapped(userId, postId);
    }

    @ResponseBody
    @GetMapping("/scrap/{user}")
    public List<PostDto> getScrapped(@PathVariable String user) {
        // 유저별 스크랩한 글 모아보기
        System.out.println("내 스크랩");
        return scrapService.getScrapped(user);
    }
}
