package smwu._back.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity(name = "Images")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name = "post_key", nullable = false)
    private Post post;

    @Column(nullable = false, name = "file_path")
    private String filePath;

}
