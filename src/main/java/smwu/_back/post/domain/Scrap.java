package smwu._back.post.domain;

import lombok.*;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;

@Entity(name = "scraps")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCRAP_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KEYPID", nullable = false)
    private UserInfoVO user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_KEY", nullable = false)
    private Post post;

}
