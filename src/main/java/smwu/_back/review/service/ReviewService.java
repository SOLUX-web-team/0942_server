package smwu._back.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu._back.review.domain.Review;
import smwu._back.review.domain.ReviewUserDto;
import smwu._back.review.repository.ReviewRepository;
import smwu._back.users.domain.UserInfoVO;
import smwu._back.users.repository.RegisterRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RegisterRepository registerRepository;
    private final ReviewRepository reviewRepository;

    public void saveReview(String userId, Map<Object, String> reviewInfo) {
        UserInfoVO user = findUser(userId);
        if (user == null) return;

        Integer score = Integer.parseInt(reviewInfo.get("REVIEW_SCORE"));
        String content = reviewInfo.get("REVIEW_CONTENT");

        Integer user_score = user.getUser_score();
        Integer user_count = user.getUser_count();

        // 유저의 거래횟수 + 1
        if (user_count == null) {
            user.setUser_count(0);
            user_count = 0;
        }
        user.setUser_count(user_count + 1);
        // 유저 평균점수
        if (user_score == null) {
            user_score = 0;
        }
        int update_score = (user_score * user_count + score) / (user_count + 1);
        user.setUser_score(update_score);

        Review review = Review.builder().user(user).review_date(LocalDateTime.now()).review_score(score).review_content(content).build();
        reviewRepository.save(review);
    }

    public List<Review> getReview(String userId) {
        UserInfoVO user = findUser(userId);
        if (user == null) return null;

        return reviewRepository.findReview(user);
    }

    public ReviewUserDto getUserInfo(String userId) {
        UserInfoVO user = findUser(userId);
        if (user == null) return null;

        return ReviewUserDto.builder().user_count(user.getUser_count()).user_score(user.getUser_score()).build();
    }

    //==유저 찾기==//
    private UserInfoVO findUser(String userId) {
        List<UserInfoVO> users = registerRepository.listUserinfoWithID(userId);
        UserInfoVO user;
        try {
            user = users.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return user;
    }
}
