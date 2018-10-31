package com.findMe.controller;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
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
            model.addAttribute("user", validateUserExists(userId));//Логика
        } catch (BadRequestException | NumberFormatException e) {//Результат
            e.printStackTrace();
            return "error404";
        } catch (InternalServerError internalServerError) {
            internalServerError.printStackTrace();
            return "error500";
        }
        return "profile2";
    }

    private User validateUserExists(String userId) throws InternalServerError, BadRequestException {//Валидация
        User userFound = userService.findUserById(Long.valueOf(userId));
        if (userFound == null)
            throw new BadRequestException("No users with such id");
        return userFound;
    }
}
