package smwu._back.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import smwu._back.repository.LoginRepository;
import smwu._back.domain.User;
import smwu._back.utils.JwtTokenProvider;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login/post")
    public @ResponseBody String loginUser (@RequestBody Map<Object, String> loginUserinfo){

        System.out.println("id:"+loginUserinfo.toString());

        String id = loginUserinfo.get("LOGIN_INPUT_ID");
        String pw = loginUserinfo.get("LOGIN_INPUT_PW");

        if(loginRepository.finduserWithID(id)==null) {
            System.out.println("null");
            return "no userinfo";
        }
        else {

            User user = loginRepository.finduserWithID(id);
            if (user == null) {
                System.out.println("no id");
                return "no userinfo";
            }
            if(passwordEncoder.matches(pw, user.getPassword())){
                System.out.println("login ok");
                return jwtTokenProvider.createToken(user.getUsername(),user.getRoles());
            } else {
                System.out.println("pw wrong");
                return "wrong pw";
            }

        }
    }
}
