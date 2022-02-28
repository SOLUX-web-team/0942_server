package smwu._back.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import smwu._back.dto.PostDto;
import smwu._back.service.PostService;
import smwu._back.service.S3Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    @ResponseBody
    @PostMapping("/write/upload")
    public PostDto create(@RequestPart(value = "post") Map<Object, String> post,
                         @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        List<String> imageList = new ArrayList<>();
        if (images != null) {
            imageList = s3Service.upload(images);
        }

        return postService.upload(post, imageList);
    }

    @ResponseBody
    @GetMapping("/main/post/{userId}/{address}")
    public List<PostDto> main(@PathVariable String userId, @PathVariable String address) {
        System.out.println("getMain");
        return postService.getPosts(userId, address);
    }

/*    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    @RequestMapping("/main/post/update/{postId}")
    public String update(@PathVariable long postId, @RequestPart(value = "post") Map<Object, String> newPost,
                         @RequestPart(value = "imgurls[]", required = false) List<String> imgurls,
                         @RequestPart(value = "images", required = false) List<MultipartFile> newImages) throws IOException {
        postService.updatePost(postId, imgurls, newPost, newImages);
        return "update success";
    }*/

    @ResponseBody
    @RequestMapping("/main/post/update/{postId}")
    public PostDto update(@PathVariable long postId, @RequestPart(value = "post") Map<Object, String> newPost) throws IOException {
        return postService.updatePost(postId, newPost);
    }

    @ResponseBody
    @RequestMapping("/main/post/delete/{postId}")
    public String delete(@PathVariable long postId) throws IOException {
        postService.deletePost(postId);
        return "delete success";
    }
}
