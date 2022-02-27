package smwu._back.post.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import smwu._back.chat.domain.ChatContent;
import smwu._back.post.domain.Post;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface MyPostRepository extends JpaRepository<Post, Long>{
    @Query("SELECT e FROM posts e WHERE e.user.keypid =?1 ")
    public LinkedList<Post> getMyAllPosts(Integer keypid);

    @Query("SELECT e FROM posts e WHERE e.user.keypid =?1 ")
    public List<Post> getMyAllPostsForList(Integer keypid);

    @Query("SELECT e FROM posts e WHERE e.id=?1 ")
    public LinkedList<Post> getSinglePostsByPostID(Long postid);

}
