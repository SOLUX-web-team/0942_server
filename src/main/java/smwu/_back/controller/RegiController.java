package smwu._back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import smwu._back.repository.RegisterRepository;
import smwu._back.domain.User;
import smwu._back.utils.JwtTokenProvider;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegiController {

    private final RegisterRepository registerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register/post")
    public @ResponseBody String registerUser (@RequestBody Map<Object, String> userinfo){
        String id = userinfo.get("USER_ID").toString();
        String password = userinfo.get("USER_PW").toString();
        String addr = userinfo.get("USER_ADDR").toString();
        String phone =userinfo.get("USER_PHONE").toString();
        String email = userinfo.get("USER_EMAIL").toString();

        System.out.println(id+password+addr+phone+email);

        //id가 같은 사용자 존재 여부 확인
        List<User> existIdLists = registerRepository.listUserinfoWithID(id);
        List<User> existEamilLists = registerRepository.listUserinfoWithEMAIL(email);
        List<User> existPhoneLists = registerRepository.listUserinfoWithPHONE(phone);

            Iterable<User> users=registerRepository.findAll(); //전체 유저 가져오기
            users.forEach(item -> System.out.println(item.getId()+" "+item.getEmail()));

        if(existIdLists.size() !=0){
            System.out.println("id already exist!");
            return "아이디가 이미 존재합니다!";
        }

        if(existEamilLists.size()!=0){
            System.out.println("email already exist!");
            return "email already exist!";
        }

        if(existPhoneLists.size()!=0) {
            System.out.println("phone already exist!");
            return "phone already exist!";
        }

        System.out.println("ok");
        final User user=
                User.builder()
                .id(id)
                .pw(passwordEncoder.encode(password))
                .email(email)
                .phone(phone)
                .addr(addr)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        registerRepository.save(user);

        return "saved! userid:"+user.getUsername();

    }
}
