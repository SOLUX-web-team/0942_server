package smwu._back.chat.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "chat_content")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_NUM")
    private Long message_num;

    @ManyToOne
    @JoinColumn(name = "CHAT_KEY", nullable = false)
    @JsonIgnore
    private Chat chat;

    @Column(name = "chat_date", nullable = false)
    private LocalDateTime chat_date;

    @Column(name = "chat_message", nullable = false)
    private String chat_message;

    @Column(name = "sender_id")
    private String sender_id;

    @Column(name = "POST_KEY")
    private Long post_key;


}
