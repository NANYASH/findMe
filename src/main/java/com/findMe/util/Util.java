package com.findMe.util;


import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    public static void validateText(String text) throws BadRequestException {
        if (text.length() > 200)
            throw new BadRequestException("Too long text. Action cannot be performed");
        if (text.contains("http") || text.contains("www"))
            throw new BadRequestException("Invalid text. Action cannot be performed");
    }

    public static Long[] validateIds(String ids) throws BadRequestException {
        if (ids == null || ids.isEmpty())
            return new Long[0];

        String[] idsStringArray = ids.split(",");
        Long[] idsArray = new Long[idsStringArray.length];

        for (int i = 0; i < idsStringArray.length; i++) {
            char[] symbols = idsStringArray[i].toCharArray();
            for (char symbol : symbols) {
                if (!Character.isDigit(symbol))
                    throw new BadRequestException("Incorrect string. Action cannot be performed.");
            }
            idsArray[i] = Long.valueOf(idsStringArray[i]);
        }
        return idsArray;
    }
}
