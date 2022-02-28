package smwu._back.dto;

import lombok.*;
import smwu._back.domain.Category;

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
