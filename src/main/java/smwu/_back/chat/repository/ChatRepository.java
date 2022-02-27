package smwu._back.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smwu._back.chat.domain.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT e FROM chat  e WHERE e.user.keypid= ?1 AND e.receiver_id = ?2 AND  e.post_key=?3")
    public List<Chat> getUserChatList ( Integer keypid, String receiver_id , Long post_key);

    @Query("SELECT e FROM  chat e WHERE e.user.keypid=?1")
    public List<Chat> getAllChatListwithKEY(Integer keypid);

}
