package com.findMe.restController;


import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.viewData.PostParametersData;
import com.findMe.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import static com.findMe.util.Util.*;

@RestController
public class PostRestController {
    private static final Logger LOGGER = Logger.getLogger(PostRestController.class);
    private PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/add-post", method = RequestMethod.POST)
    public ResponseEntity createNewPost(HttpSession session, @ModelAttribute PostParametersData postParametersData) throws UnauthorizedException, BadRequestException, InternalServerError {
        postParametersData.setUserPosted(validateLogIn(session));
        postService.addPost(postParametersData);
        LOGGER.info("Post added.");
        return new ResponseEntity("Request is sent.", HttpStatus.OK);
    }

}
