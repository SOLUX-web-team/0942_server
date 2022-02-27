package smwu._back.review.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import smwu._back.post.domain.Post;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "reviews")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KEYPID", nullable = false)
    @JsonIgnore
    private UserInfoVO user;

    @Column(nullable = false)
    private Integer review_score;

    @Column(nullable = false)
    private String review_content;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime review_date;
}
