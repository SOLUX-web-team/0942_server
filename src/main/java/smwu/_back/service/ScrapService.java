package smwu._back.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu._back.domain.Post;
import smwu._back.dto.PostDto;
import smwu._back.domain.Scrap;
import smwu._back.repository.PostRepository;
import smwu._back.repository.ScrapRepository;
import smwu._back.repository.RegisterRepository;
import smwu._back.domain.UserInfoVO;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ScrapService {

    private final RegisterRepository registerRepository;
    private final PostRepository postRepository;
    private final ScrapRepository scrapRepository;
    private final PostService postService;

    public Integer saveScrap(String userId, Long postId) {
        UserInfoVO user = findUser(userId);
        if (user == null) return 0;

        Post post = postRepository.findOne(postId);

        List<Scrap> isExist = scrapRepository.findScrap(user, post);
//        System.out.println(isExist);
        System.out.println("before " + post.getScrap_num());
        if (isExist.size() == 0) {
            Scrap scrap = Scrap.builder().user(user).post(post).build();
//            user.getScraps().add(scrap);
            post.setScrap_num(post.getScrap_num() + 1);
            scrapRepository.save(scrap);
        } else {
            Scrap one = scrapRepository.findOne(isExist.get(0).getId());
            scrapRepository.deleteScrap(one);
            user.getScraps().remove(one);
            post.setScrap_num(post.getScrap_num() - 1);
        }
        System.out.println("after " + post.getScrap_num());
        System.out.println(user.getScraps());

        return post.getScrap_num();
    }


    public Integer isScrapped(String userId, Long postId) {
        UserInfoVO user = findUser(userId);
        if (user == null) return 0;

        Post post = postRepository.findOne(postId);

        List<Scrap> scrap = scrapRepository.findScrap(user, post);
        return scrap.size();
    }

    public List<PostDto> getScrapped(String userId) {
        UserInfoVO user = findUser(userId);
        if (user == null) return null;

        List<Scrap> scraps = user.getScraps();
        List<Post> scrap_posts = new ArrayList<>();
        for (Scrap scrap : scraps) {
            scrap_posts.add(scrap.getPost());
        }
        return postService.getPostDtos(scrap_posts);
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
