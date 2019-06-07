package com.findMe.controller;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;
import com.findMe.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import java.time.LocalDate;

import static com.findMe.util.Util.validateLogIn;

@Controller
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user-registration")
    public ResponseEntity registerUser(@ModelAttribute User user) throws BadRequestException, InternalServerError {
        userService.create(user);
        LOGGER.info("User (id: " + user.getId() + ") registered.");
        return new ResponseEntity("User is registered.", HttpStatus.CREATED);
    }

    @PostMapping(path = "/user-update")
    public ResponseEntity updateUser(@ModelAttribute User user) throws BadRequestException, InternalServerError {
        userService.update(user);
        LOGGER.info("User (id: " + user.getId() + ") updated.");
        return new ResponseEntity("User is updated.", HttpStatus.OK);
    }

    @GetMapping(path = "/login")
    public ResponseEntity logIn(HttpSession session, @RequestParam String email, @RequestParam String password) throws BadRequestException, InternalServerError {
        if (session.getAttribute("user") != null)
            return new ResponseEntity("User is already logged in.", HttpStatus.FORBIDDEN);

        User foundUser = userService.login(email, password);
        session.setAttribute("user", foundUser);
        LOGGER.info("User (id: " + foundUser.getId() + ") is logged.");
        return new ResponseEntity<>(foundUser.getId(), HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity logOut(HttpSession session) throws BadRequestException, InternalServerError, UnauthorizedException {
        User user = validateLogIn(session);
        userService.logout(user, LocalDate.now());
        session.setAttribute("user", null); // or session.removeAttribute("id");
        LOGGER.info("User (id: " + user.getId() + ") is logged out.");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/user-registration")
    public String getRegisterPage() {
        return "registerPage";
    }

}
