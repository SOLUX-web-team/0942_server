package smwu._back.post.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    private String userId;

    private Integer writer_score;

    private String title;

    private Category category; //FOOD, ITEM

    private Integer invite_num;

    private LocalDateTime date;

    private Integer scrap_num;

    private String content;

    private List<String> images = new ArrayList<>();

    private String place;

    private Integer cost;

    private String post_addr;

}
