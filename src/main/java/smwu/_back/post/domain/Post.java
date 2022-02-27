package smwu._back.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import smwu._back.users.domain.UserInfoVO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "posts")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_KEY")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KEYPID", nullable = false)
    @JsonIgnore
    private UserInfoVO user;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category; //FOOD, ITEM

    @Column(nullable = false)
    private Integer invite_num;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer scrap_num;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonBackReference //순환참조 방지
    private List<Image> images = new ArrayList<>();

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private Integer cost;

    @Column
    private String post_addr;

    //==생성 메서드==//
    public static Post createPost(UserInfoVO user, String title, String category, Integer invite_num,
                                  Integer scrap_num, String content, String place, Integer cost, String post_addr) {
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setDate(LocalDateTime.now());
        if (category.equals("음식")) {
            post.setCategory(Category.FOOD);
        } else if (category.equals("물건")) {
            post.setCategory(Category.ITEM);
        }
        post.setInvite_num(invite_num);
        post.setScrap_num(scrap_num);
        post.setContent(content);
        post.setPlace(place);
        post.setCost(cost);
        post.setPost_addr(post_addr);

        return post;
    }
}
