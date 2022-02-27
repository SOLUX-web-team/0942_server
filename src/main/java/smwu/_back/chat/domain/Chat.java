package smwu._back.chat.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;

@Entity(name = "chat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_KEY")
    private Long chat_key ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KEYPID", nullable = false)
    @JsonIgnore
    private UserInfoVO user;

    @Column(nullable = false)
    private String receiver_id;

    @Column(name = "POST_KEY")
    private Long post_key;

}
