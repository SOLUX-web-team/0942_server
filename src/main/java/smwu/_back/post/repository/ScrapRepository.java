package smwu._back.post.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smwu._back.post.domain.Post;
import smwu._back.post.domain.Scrap;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScrapRepository {

    private final EntityManager em;

    public void save(Scrap scrap) {
        em.persist(scrap);
    }

    public Scrap findOne(Long scrapId) {
        return em.find(Scrap.class, scrapId);
    }

    public List<Scrap> findScrap(UserInfoVO user, Post post) {
        return em.createQuery("select s from scraps s where s.user = :user and s.post = :post", Scrap.class)
                .setParameter("user", user)
                .setParameter("post", post)
                .getResultList();
    }

    public void deleteScrap(Scrap scrap) {
        em.remove(scrap);
    }
}
