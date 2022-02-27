package smwu._back.users.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import smwu._back.post.domain.Image;
import smwu._back.post.domain.Scrap;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Users")
public class UserInfoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keypid;

    @Column(nullable = false, unique = true, length = 30)
    private String id;

    @Column(nullable = false, length = 30)
    private String pw;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String phone;

    @Column(nullable = false,  length = 150)
    private String addr;

    @Column
    private String current_addr; // api로 받아온 주소 도/시/구/동까지 저장

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

    @Column
    private Integer user_score;

    @Column
    private Integer user_count;

    public UserInfoVO(String id, String pw, String email, String phone, String addr){

        this.id=id;
        this.pw=pw;
        this.email = email;
        this.phone = phone;
        this.addr = addr;
    }


    //getter setter

    public Integer getKey() {
        return keypid;
    }

    public void setKey(Integer keypid) {
        this.keypid = keypid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }


}
