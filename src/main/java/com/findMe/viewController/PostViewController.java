package com.findMe.viewController;

import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.Post;
import com.findMe.model.User;
import com.findMe.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.findMe.util.Util.validateLogIn;

@Controller
public class PostViewController {
    private static final Logger LOGGER = Logger.getLogger(PostViewController.class);
    private PostService postService;

    @Autowired
    public PostViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/feed")
    public String findPostsByFriendsPages(HttpSession session, Model model, @RequestParam(required = false) String currentOffset) throws UnauthorizedException, InternalServerError {
        Integer offset = 0;
        List<Post> posts;
        User userSession = validateLogIn(session);
        if (currentOffset == null) {
            posts = postService.getByFriendsPages(userSession.getId(), offset);
            offset = 10;
        } else {
            offset = Integer.valueOf(currentOffset);
            posts = postService.getByFriendsPages(userSession.getId(), offset);
            offset += 10;
        }
        model.addAttribute("news", posts);
        model.addAttribute("offset", offset);
        LOGGER.info("News are opened");
        return "news";
    }
}
