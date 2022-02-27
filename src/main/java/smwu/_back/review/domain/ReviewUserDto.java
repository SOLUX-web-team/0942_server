package smwu._back.review.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewUserDto {

    private Integer user_count;

    private Integer user_score;
}
