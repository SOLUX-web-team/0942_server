package smwu._back.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smwu._back.users.repository.RegisterRepository;
import smwu._back.users.domain.UserInfoVO;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RegiController {

    private final RegisterRepository registerRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/register/post")
//    public @ResponseBody String registerUser (@RequestParam("USER_PW") String id,@RequestParam("") String email, @RequestParam String password, @RequestParam String phone, @RequestParam String addr ){
    public @ResponseBody String registerUser (@RequestBody Map<Object, String> userinfo){
        String id = userinfo.get("USER_ID").toString();
        String password = userinfo.get("USER_PW").toString();
        String addr = userinfo.get("USER_ADDR").toString();
        String phone =userinfo.get("USER_PHONE").toString();
        String email = userinfo.get("USER_EMAIL").toString();

        System.out.println(id+password+addr+phone+email);



        //id가 같은 사용자 존재 여부 확인
        List<UserInfoVO> existIdLists = registerRepository.listUserinfoWithID(id);
        List<UserInfoVO> existEamilLists = registerRepository.listUserinfoWithEMAIL(email);
        List<UserInfoVO> existPhoneLists = registerRepository.listUserinfoWithPHONE(phone);

            Iterable<UserInfoVO> users=registerRepository.findAll(); //전체 유저 가져오기
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
            final UserInfoVO user=UserInfoVO.builder().id(id).pw(password).email(email).phone(phone).addr(addr).build();
            registerRepository.save(user);

//            Iterable<UserInfoVO> users=registerRepository.findAll(); //전체 유저 가져오기
//            users.forEach(item -> System.out.println(item.getId()+" "+item.getEmail()));
            return "saved!";

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    @RequestMapping(value = "/register/get", method = RequestMethod.GET)
    public String test1(){
        return "ddddd";
    }
}
