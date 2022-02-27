package smwu._back.users.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smwu._back.users.repository.LoginRepository;
import smwu._back.users.domain.UserInfoVO;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginRepository loginRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/login/post")
    public @ResponseBody String loginUser (@RequestBody Map<Object, String> loginUserinfo){

        System.out.println("id:"+loginUserinfo.toString());

        if(loginRepository.finduserWithID(loginUserinfo.get("LOGIN_INPUT_ID").toString())==null) {
            System.out.println("null");
            return "no userinfo";
        }
        UserInfoVO user = loginRepository.finduserWithID(loginUserinfo.get("LOGIN_INPUT_ID").toString());
        if(user==null){
            System.out.println("no id");
            return "no userinfo";
        }
        if(user.getPw().equals(loginUserinfo.get("LOGIN_INPUT_PW").toString())){
            System.out.println("login ok");
            return "login ok";
        }
        else{
            System.out.println("pw wrong");
            return "wrong pw";
        }
    }
}
