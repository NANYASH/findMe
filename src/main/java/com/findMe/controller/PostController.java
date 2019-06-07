package com.findMe.controller;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;
import com.findMe.model.enums.RoleName;
import com.findMe.model.viewData.PostParametersData;
import com.findMe.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import static com.findMe.util.Util.*;

@Controller
public class PostController {
    private static final Logger LOGGER = Logger.getLogger(PostController.class);
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/add-post")
    public ResponseEntity createNewPost(HttpSession session, @ModelAttribute PostParametersData postParametersData) throws UnauthorizedException, BadRequestException, InternalServerError {
        postParametersData.setUserPosted(validateLogIn(session));
        postService.create(postParametersData);
        LOGGER.info("Post added.");
        return new ResponseEntity("Post is sent.", HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/remove-post/{postId}")
    public ResponseEntity deletePost(HttpSession session, @PathVariable String postId) throws UnauthorizedException, BadRequestException, InternalServerError {
        validateLogIn(session);
        postService.delete(convertId(postId));
        User userSession = (User) session.getAttribute("user");
        System.out.println(userSession.getRoles().toString());
        LOGGER.info("Post id: "+postId+" removed.");
        return new ResponseEntity("Post is removed.", HttpStatus.OK);
    }

}
