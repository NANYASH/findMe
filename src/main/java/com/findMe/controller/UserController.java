package com.findMe.controller;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.NotFoundException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;
import com.findMe.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.POST)
    public ResponseEntity registerUser(@ModelAttribute User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>("User is registered.", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>("User with such username/email already exists.", HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/user-registration", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "registerPage";
    }


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity logIn(HttpSession session ,@RequestParam String email, @RequestParam String password) {
        try {
            User foundUser = userService.login(email,password);
            session.setAttribute("id", foundUser.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Incorrect credentials.", HttpStatus.UNAUTHORIZED);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>("InternalServerError.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity logOut(HttpSession session) throws BadRequestException {
        session.removeAttribute("user");// or session.setAttribute("user",null);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        try {
            User userFound = userService.findUserById(userId);
            model.addAttribute("user", userFound);
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

}
