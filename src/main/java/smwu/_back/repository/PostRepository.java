package smwu._back.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smwu._back.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findPosts(String currentAddress) {
        return em.createQuery("select m from posts m where m.post_addr = :addr", Post.class)
                .setParameter("addr", currentAddress)
                .getResultList();
    }

    public void delete(Post post) {
        em.remove(post);
    }

}
