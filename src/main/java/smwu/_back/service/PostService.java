package smwu._back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu._back.domain.Category;
import smwu._back.domain.Image;
import smwu._back.domain.Post;
import smwu._back.dto.PostDto;
import smwu._back.repository.ImageRepository;
import smwu._back.repository.PostRepository;
import smwu._back.repository.RegisterRepository;
import smwu._back.domain.UserInfoVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RegisterRepository registerRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    public PostDto upload(Map<Object, String> postInfo, List<String> files) {
        String userId = postInfo.get("USER_ID");
        UserInfoVO user = findUser(userId);
        if (user == null) return null;

        String title = postInfo.get("POST_TITLE");
        String category = postInfo.get("POST_CATEGORY");
        Integer invite_num = Integer.parseInt(postInfo.get("POST_INVITE_NUM"));
        Integer scrap_num = Integer.parseInt(postInfo.get("POST_SCRAP_NUM"));
        String content = postInfo.get("POST_CONTENT");
        String place = postInfo.get("POST_PLACE");
        Integer cost = Integer.parseInt(postInfo.get("POST_COST"));
        String post_addr;
        if (user.getCurrent_addr() != null) {
            post_addr = user.getCurrent_addr();
        } else {
            post_addr = "";
        }

        Post post = Post.createPost(user, title, category, invite_num, scrap_num, content, place, cost, post_addr);

        if (!files.isEmpty()) {
            List<Image> images = new ArrayList<>();
            for (String file : files) {
                Image imageFile = Image.builder()
                        .post(post)
                        .filePath(file)
                        .build();
                imageRepository.save(imageFile);
                images.add(imageFile);
            }
            post.setImages(images);
        }

        postRepository.save(post);

        PostDto singlepost = PostDto.builder()
                .id(post.getId())
                .category(post.getCategory())
                .content(post.getContent())
                .cost(post.getCost())
                .date(post.getDate())
                .title(post.getTitle())
                .scrap_num(post.getScrap_num())
                .userId(post.getUser().getId())
                .invite_num(post.getInvite_num())
                .place(post.getPlace())
                .build();
        List<Image> images = post.getImages();
        List<String> imageurls = new ArrayList<>();
        for (Image img : images) {
            imageurls.add("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + img.getFilePath());
        }
        singlepost.setImages(imageurls);

        return singlepost;
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPosts(String userId, String address) {
        UserInfoVO user = findUser(userId);
        if (user == null || !user.getCurrent_addr().equals(address)) {
            List<Post> all = postRepository.findPosts(address);
            System.out.println("user == null " + address);
            return getPostDtos(all);
        }

        String currentAddress = user.getCurrent_addr();
        System.out.println("유저: " + user + " " + "현재주소: " + currentAddress);

        List<Post> all = postRepository.findPosts(currentAddress);
        return getPostDtos(all);
    }


    public void deletePost(Long id) throws IOException {
        Post post = postRepository.findOne(id);
        List<Image> images = post.getImages();
        for (Image image : images) {
            s3Service.delete(image.getFilePath());
        }
        postRepository.delete(post);
    }

    /*public void updatePost(Long id, List<String> imgurls, Map<Object, String> newPost, List<MultipartFile> newImages) throws IOException {
        Post post = postRepository.findOne(id);
        List<Image> newImageList = new ArrayList<>(); // 새로 저장할 이미지 객체

        if (imgurls.size() != 0) {
            for (String imgurl : imgurls) {
                String path = imgurl.substring(38); // folder명/filename 으로 자르기
                s3Service.delete(path); // s3 저장소에서 삭제
                imageRepository.delete(path); // db에서 삭제
            }
        }
        if (newImages != null) {
            List<String> uploadImageList = s3Service.upload(newImages); // 새로 업로드한 파일 경로들

            List<Image> originalImages = post.getImages(); // 원래 저장되어 있던 이미지
            for (Image origin : originalImages) {
                uploadImageList.add(origin.getFilePath());
            }

            for (String file : uploadImageList) {
                Image imageFile = Image.builder()
                        .post(post)
                        .filePath(file)
                        .build();
                imageRepository.save(imageFile);
                newImageList.add(imageFile);
            }
            post.setImages(newImageList);
        }

        String title = newPost.get("POST_TITLE");
        String category = newPost.get("POST_CATEGORY");
        Integer invite_num = Integer.parseInt(newPost.get("POST_INVITE_NUM"));
        Integer scrap_num = Integer.parseInt(newPost.get("POST_SCRAP_NUM"));
        String content = newPost.get("POST_CONTENT");
        String place = newPost.get("POST_PLACE");
        Integer cost = Integer.parseInt(newPost.get("POST_COST"));

        post.setTitle(title);
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
    }*/

    public PostDto updatePost(Long id, Map<Object, String> newPost) {
        Post post = postRepository.findOne(id);

        String title = newPost.get("POST_TITLE");
        String category = newPost.get("POST_CATEGORY");
        Integer invite_num = Integer.parseInt(newPost.get("POST_INVITE_NUM"));
        Integer scrap_num = Integer.parseInt(newPost.get("POST_SCRAP_NUM"));
        String content = newPost.get("POST_CONTENT");
        String place = newPost.get("POST_PLACE");
        Integer cost = Integer.parseInt(newPost.get("POST_COST"));

        post.setTitle(title);
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

        PostDto singlepost = PostDto.builder()
                .id(post.getId())
                .category(post.getCategory())
                .content(post.getContent())
                .cost(post.getCost())
                .date(post.getDate())
                .title(post.getTitle())
                .scrap_num(post.getScrap_num())
                .userId(post.getUser().getId())
                .invite_num(post.getInvite_num())
                .place(post.getPlace())
                .build();
        List<Image> images = post.getImages();
        List<String> imageurls = new ArrayList<>();
        for (Image img : images) {
            imageurls.add("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + img.getFilePath());
        }
        singlepost.setImages(imageurls);

        return singlepost;
    }

    //==PostDto 반환 메소드==//
    List<PostDto> getPostDtos(List<Post> all) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : all) {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .userId(post.getUser().getId())
                    .writer_score(post.getUser().getUser_score())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .invite_num(post.getInvite_num())
                    .date(post.getDate())
                    .scrap_num(post.getScrap_num())
                    .content(post.getContent())
                    .place(post.getPlace())
                    .cost(post.getCost())
                    .post_addr(post.getPost_addr())
                    .build();
            List<Image> images = post.getImages();
            List<String> imageurls = new ArrayList<>();
            for (Image img : images) {
                imageurls.add("https://" + S3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + img.getFilePath());
            }
            postDto.setImages(imageurls);
            postDtoList.add(postDto);
        }

        LinkedList<PostDto> reversePostDtoList = new LinkedList<>();

        for (int i = postDtoList.size() - 1; i > -1; i--) {
            reversePostDtoList.add(postDtoList.get(i));
        }
        return reversePostDtoList;
    }

    //==유저 찾기==//
    private UserInfoVO findUser(String userId) {
        List<UserInfoVO> users = registerRepository.listUserinfoWithID(userId);
        UserInfoVO user;
        try {
            user = users.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return user;
    }
}
