package smwu._back.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smwu._back.domain.Review;
import smwu._back.dto.ReviewUserDto;
import smwu._back.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;

    @ResponseBody
    @PostMapping("/review/to/{userId}")
    public String writeReview(@PathVariable String userId, @RequestBody Map<Object, String> review) {
        reviewService.saveReview(userId, review);
        return "write review ok";
    }

    @ResponseBody
    @GetMapping("/review/all/{userId}")
    public List<Review> getReviews(@PathVariable String userId) {
        return reviewService.getReview(userId);
    }

    @ResponseBody
    @GetMapping("/review/userInfo/{userId}")
    public ReviewUserDto getUserReviewInfo(@PathVariable String userId) {
        return reviewService.getUserInfo(userId);
    }
}
