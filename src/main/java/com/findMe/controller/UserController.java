package com.findMe.controller;

import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;
import com.findMe.model.viewData.PostFilterData;
import com.findMe.service.PostService;
import com.findMe.service.RelationshipService;
import com.findMe.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import java.time.LocalDate;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.validateLogIn;

@Controller
public class UserController {
    private UserService userService;
    private RelationshipService relationshipService;
    private PostService postService;

    @Autowired
    public UserController(UserService userService, RelationshipService relationshipService, PostService postService) {
        this.userService = userService;
        this.relationshipService = relationshipService;
        this.postService = postService;
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.POST)
    public ResponseEntity registerUser(@ModelAttribute User user) {
        try {
            userService.registerUser(user);
            Logger.getLogger("rootLogger").info("User registered.");
            return new ResponseEntity("User is registered.", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return new ResponseEntity("User with such username/email already exists.", HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").fatal("InternalServerError: "+e.getMessage());
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity logIn(HttpSession session, @RequestParam String email, @RequestParam String password) {
        try {
            if (session.getAttribute("user") != null)
                return new ResponseEntity("User is already logged in.", HttpStatus.FORBIDDEN);

            User foundUser = userService.login(email, password);
            session.setAttribute("user", foundUser);
            Logger.getLogger("rootLogger").info("User logged.");
            return new ResponseEntity<>(foundUser.getId(), HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (InternalServerError e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").fatal("BadRequestException: "+e.getMessage());
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity logOut(HttpSession session) throws BadRequestException, InternalServerError {
        User user;
        try {
            user = validateLogIn(session);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        userService.logout(user, LocalDate.now());
        session.setAttribute("user", null); // or session.removeAttribute("id");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session, Model model, @PathVariable String userId, @ModelAttribute PostFilterData postFilterData) {
        try {
            Long userProfileId = convertId(userId);
            postFilterData.setUserPageId(userProfileId);
            User userSession = (User) session.getAttribute("user");
            User foundUserProfile = userService.findUserById(userProfileId);

            model.addAttribute("user", foundUserProfile);
            model.addAttribute("posts",  postService.findPostsByPage(postFilterData));

            if (userSession != null) {

                RelationshipStatus relationshipStatus = relationshipService.findStatusById(userSession.getId(), userProfileId);

                if (relationshipStatus != null) {
                    model.addAttribute("status", relationshipStatus.toString());

                    if (relationshipStatus.equals(RelationshipStatus.ACCEPTED))
                        model.addAttribute("friends", relationshipService.findByRelationshipStatus(userProfileId, RelationshipStatus.ACCEPTED));

                } else if (userSession.equals(foundUserProfile)) {
                    model.addAttribute("status", RelationshipStatus.MY_PROFILE.toString());
                    model.addAttribute("outgoingRequests", relationshipService.findOutgoingRequests(userProfileId));
                    model.addAttribute("incomingRequests", relationshipService.findIncomingRequests(userProfileId));
                    model.addAttribute("friends", relationshipService.findByRelationshipStatus(userProfileId, RelationshipStatus.ACCEPTED));
                }
            }
            Logger.getLogger("rootLogger").info("User page opened.");
        } catch (BadRequestException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return "error400";
        } catch (NotFoundException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("NotFoundException: "+e.getMessage());
            return "error404";
        } catch (InternalServerError e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").fatal("BadRequestException: "+e.getMessage());
            return "error500";
        }
        return "profile";
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "registerPage";
    }

}
