package com.findMe.controller;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.model.User;
import com.findMe.service.FriendsService;
import com.findMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.convertRelationshipStatus;

@Controller
public class UserController {
    private UserService userService;
    private FriendsService friendsService;

    @Autowired
    public UserController(UserService userService, FriendsService friendsService) {
        this.userService = userService;
        this.friendsService = friendsService;
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.POST)
    public ResponseEntity registerUser(@ModelAttribute User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity("User is registered.", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity("User with such username/email already exists.", HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "registerPage";
    }


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity logIn(HttpSession session, @RequestParam String email, @RequestParam String password) {
        try {
            if (session.getAttribute("id") != null)
                return new ResponseEntity("User is already logged in.", HttpStatus.FORBIDDEN);
            User foundUser = userService.login(email, password);
            session.setAttribute("id", foundUser.getId());
            return new ResponseEntity(HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity logOut(HttpSession session) throws BadRequestException {
        if (session.getAttribute("id") == null)
            return new ResponseEntity("User is not logged in.", HttpStatus.UNAUTHORIZED);

        session.setAttribute("id", null); // or session.removeAttribute("id");
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        try {
            Long userToId = convertId(userId);
            User userFound = userService.findUserById(userToId);
            List<User> friends = friendsService.findByRelationshipStatus(userToId, RelationshipStatus.ACCEPTED);

            model.addAttribute("user", userFound);
            model.addAttribute("friends",friends);
            //model.addAttribute("requestsFrom");
            //model.addAttribute("requestsTo");
        } catch (BadRequestException e) {
            e.printStackTrace();
            return "page400";
        } catch (NotFoundException e) {
            e.printStackTrace();
            return "error404";
        } catch (InternalServerError e) {
            e.printStackTrace();
            return "error500";
        }
        return "profilePage2";
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseEntity addRelationship(HttpSession session, @RequestParam String userToId) {
        Long userFromId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.addRelationship(userFromId, convertId(userToId));
            return new ResponseEntity("Request is sent.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.POST)
    public ResponseEntity deleteRelationship(HttpSession session, @RequestParam String userToId) {
        Long userFromId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.deleteRelationship(userFromId, convertId(userToId));
            return new ResponseEntity("User is deleted from friends./Request is deleted.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseEntity updateRelationship(HttpSession session, @RequestParam String userFromId, @RequestParam String status) {
        Long userToId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.updateRelationship(convertId(userFromId), userToId, convertRelationshipStatus(status));
            return new ResponseEntity("Relationship status is changed to" + status.toString(), HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/rejectRequest", method = RequestMethod.POST)
    public ResponseEntity rejectRequest(HttpSession session, @RequestParam String userToId) {
        Long userFromId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.rejectRequest(userFromId, convertId(userToId));
            return new ResponseEntity("Request is rejected.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity("InternalServerError", HttpStatus.BAD_REQUEST);
        }
    }
}
