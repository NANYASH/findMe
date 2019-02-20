package com.findMe.controller;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.Post;
import com.findMe.model.User;
import com.findMe.model.viewData.PostParametersData;
import com.findMe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.findMe.util.Util.*;

@Controller
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/add-post", method = RequestMethod.POST)
    public ResponseEntity createNewPost(HttpSession session, @ModelAttribute PostParametersData postParametersData) throws UnauthorizedException {
        try {
            postParametersData.setUserPosted(validateLogIn(session));
            postService.addPost(postParametersData);
            return new ResponseEntity("Request is sent.", HttpStatus.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    public String findPostsByFriendsPages(HttpSession session, Model model, @RequestParam(required = false) String currentSize) {
        Long offset = 0L;
        List<Post> posts;

        try {
            User userSession = validateLogIn(session);
            if (currentSize == null) {
                posts = postService.findPostsByFriendsPages(userSession.getId(), offset);
                offset = 1L;
            }else {
                offset = Long.valueOf(currentSize);
                posts = postService.findPostsByFriendsPages(userSession.getId(), offset);
                offset+=1;
            }
            model.addAttribute("news", posts);
            model.addAttribute("offset", offset);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        } catch (InternalServerError e) {
            e.printStackTrace();
        }
        return "news";
    }

}
