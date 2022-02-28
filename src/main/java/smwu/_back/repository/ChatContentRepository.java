package smwu._back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smwu._back.domain.ChatContent;

import java.util.List;

@Repository
public interface ChatContentRepository extends JpaRepository<ChatContent, Long> {

    @Query("SELECT e FROM chat_content e WHERE e.chat.chat_key= ?1 AND e.post_key=?2")
    public List<ChatContent> getAllMessage (Long chat_key, Long post_key);

//    @Query("SELECT  e FROM  posts  e WHERE  e.user.keypid=?1")
//    public List<Post> getUserAllPost(Integer id);

    @Query("SELECT e FROM chat_content  e WHERE e.chat.chat_key =?1")
    public List<ChatContent> findMyAllChatContent(Long chat_key);
}
