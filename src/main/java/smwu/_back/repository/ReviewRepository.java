package smwu._back.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smwu._back.domain.Review;
import smwu._back.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public List<Review> findReview(User user) {
        return em.createQuery("select r from reviews r where r.user = :user", Review.class)
                .setParameter("user", user)
                .getResultList();
    }
}
