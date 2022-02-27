package smwu._back.review.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smwu._back.review.domain.Review;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findReview(UserInfoVO user) {
        return em.createQuery("select r from reviews r where r.user = :user", Review.class)
                .setParameter("user", user)
                .getResultList();
    }
}
