package com.findMe.util;


import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;

import javax.servlet.http.HttpSession;

public class Util {
    public static Long convertId(String id) throws BadRequestException {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new BadRequestException("Cannot be converted to Long type");
        }
    }

    public static RelationshipStatus convertRelationshipStatus(String status) throws BadRequestException {
        try {
            return RelationshipStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new BadRequestException("Cannot be converted to RelationshipStatus type");
        }
    }

    public static User validateLogIn(HttpSession session) throws UnauthorizedException {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new UnauthorizedException("User should be logged in.");
        return user;
    }
}
