package com.findMe.controller;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;
import com.findMe.service.RelationshipService;
import com.findMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.convertRelationshipStatus;
import static com.findMe.util.Util.validateLogIn;

@Controller
public class UserController {
    private UserService userService;
    private RelationshipService friendsService;

    @Autowired
    public UserController(UserService userService, RelationshipService friendsService) {
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

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity logIn(HttpSession session, @RequestParam String email, @RequestParam String password) {
        try {
            if (session.getAttribute("user") != null)
                return new ResponseEntity("User is already logged in.", HttpStatus.FORBIDDEN);

            User foundUser = userService.login(email, password);
            session.setAttribute("user", foundUser);
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
        try {
            validateLogIn(session);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("user", null); // or session.removeAttribute("id");
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(HttpSession session,Model model, @PathVariable String userId) {
        try {
            Long convertedUserId = convertId(userId);

            model.addAttribute("user", userService.findUserById(convertedUserId));
            model.addAttribute("status", friendsService.findStatusById(validateLogIn(session).getId(),convertedUserId).toString());
            model.addAttribute("friends", userService.findByRelationshipStatus(convertedUserId, RelationshipStatus.ACCEPTED));
            model.addAttribute("requestsFrom", userService.findRequestedFrom(convertedUserId));
            model.addAttribute("requestsTo", userService.findRequestedTo(convertedUserId));
        } catch (BadRequestException e) {
            e.printStackTrace();
            return "error400";
        } catch (NotFoundException e) {
            e.printStackTrace();
            return "error404";
        } catch (InternalServerError e) {
            e.printStackTrace();
            return "error500";
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            return "error401";
        }
        return "profilePage";
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseEntity addRelationship(HttpSession session, @RequestParam String userToId) {
        try {
            friendsService.addRelationship(validateLogIn(session).getId(), convertId(userToId));
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

    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.POST)
    public ResponseEntity deleteRelationship(HttpSession session, @RequestParam String userToId) {
        try {
            friendsService.deleteRelationship(validateLogIn(session).getId(), convertId(userToId));
            return new ResponseEntity("User is deleted from friends./Request is deleted.", HttpStatus.OK);
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

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseEntity updateRelationship(HttpSession session, @RequestParam String userFromId, @RequestParam String status) {
        try {
            friendsService.updateRelationship(convertId(userFromId), validateLogIn(session).getId(), convertRelationshipStatus(status));
            return new ResponseEntity("Relationship status is changed to" + status.toString(), HttpStatus.OK);
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

    @RequestMapping(path = "/rejectRequest", method = RequestMethod.POST)
    public ResponseEntity rejectRequest(HttpSession session, @RequestParam String userToId) {
        try {
            friendsService.rejectRequest(validateLogIn(session).getId(), convertId(userToId));
            return new ResponseEntity("Request is rejected.", HttpStatus.OK);
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

    @RequestMapping(path = "/user-registration", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "registerPage";
    }

}
