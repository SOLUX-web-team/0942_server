package smwu._back.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smwu._back.domain.Image;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public void delete(String path) {
        Image image = em.createQuery("select i from Images i where i.filePath = :path", Image.class)
                .setParameter("path", path)
                .getSingleResult();
        em.remove(image);
    }
}
