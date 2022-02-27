package smwu._back.post.controller;

import lombok.AllArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.web.bind.annotation.*;
import smwu._back.post.domain.Image;
import smwu._back.post.domain.Post;
import smwu._back.post.domain.PostDto;
import smwu._back.post.repository.MyPostRepository;
import smwu._back.post.repository.PostRepository;
import smwu._back.post.service.PostService;
import smwu._back.post.service.S3Service;
import smwu._back.users.domain.UserInfoVO;
import smwu._back.users.repository.FindUserRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MyPostController {

    private final PostService postService;
    private final S3Service s3Service;
    private final MyPostRepository myPostRepository;
    private final FindUserRepository findUserRepository;

    @ResponseBody
    @PostMapping("/mypage/postlist")
    public List<PostDto> sendMypostList(@RequestBody Map<Object, String> userinfo){
        System.out.println(userinfo.get("MY_ID"));
        UserInfoVO me =findUserRepository.finduserWithID(userinfo.get("MY_ID"));

        LinkedList<Post> myposts = myPostRepository.getMyAllPosts(me.getKeypid());
//        List<Post> mypostsforList =myPostRepository.getMyAllPostsForList(me.getKeypid());

        myposts.forEach(post -> {
            System.out.println(post.getId()+post.getTitle());
        });



        LinkedList<PostDto> postDtoList = new LinkedList<>();
        for (Post post : myposts) {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .userId(post.getUser().getId())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .invite_num(post.getInvite_num())
                    .date(post.getDate())
                    .scrap_num(post.getScrap_num())
                    .content(post.getContent())
                    .place(post.getPlace())
                    .cost(post.getCost())
                    .build();
            List<Image> images = post.getImages();
            List<String> imageurls = new ArrayList<>();
            for (Image img : images) {
                imageurls.add("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + img.getFilePath());
            }
            postDto.setImages(imageurls);
            postDtoList.add(postDto);
        }

        LinkedList<PostDto> reversePostDtoList =new LinkedList<>();

        for (int i=postDtoList.size()-1; i>-1; i--){
            reversePostDtoList.add(postDtoList.get(i));
        }
        return reversePostDtoList;
    }

    @ResponseBody
    @PostMapping("/getSinglePost")
    public PostDto sendSinglePost(@RequestBody Map<Object, String> postid){

        System.out.println("받은 포스트 아이디:"+postid.get("postID"));
        LinkedList<Post> oneposts = myPostRepository.getSinglePostsByPostID(Long.valueOf(postid.get("postID")));

        System.out.println(oneposts);

        PostDto singlepost = PostDto.builder()
                .id(oneposts.get(0).getId())
                .category(oneposts.get(0).getCategory())
                .content(oneposts.get(0).getContent())
                .cost(oneposts.get(0).getCost())
                .date(oneposts.get(0).getDate())
                .title(oneposts.get(0).getTitle())
                .scrap_num(oneposts.get(0).getScrap_num())
                .userId(oneposts.get(0).getUser().getId())
                .invite_num(oneposts.get(0).getInvite_num())
                .place(oneposts.get(0).getPlace())
                .build();
        List<Image> images = oneposts.get(0).getImages();
        List<String> imageurls = new ArrayList<>();
        for (Image img : images) {
            imageurls.add("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + img.getFilePath());
        }
        singlepost.setImages(imageurls);


        return singlepost;
    }
}
