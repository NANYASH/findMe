package com.findMe.controller;

import com.findMe.exception.BadRequestException;
import com.findMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) {
        try {
            model.addAttribute("user", userService.findUserById(Long.valueOf(userId)));
            return  "profile2";
        } catch (BadRequestException e) {
            e.printStackTrace();
            return  "error404";
        }
    }
}
