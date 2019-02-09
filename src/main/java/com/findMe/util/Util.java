package com.findMe.util;


import com.findMe.model.enums.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.User;

import javax.servlet.http.HttpSession;
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

    public static String createUsersTaggedNamesString(List<User> usersTagged){
        StringBuffer resultString = new StringBuffer();
        resultString.append("Tagged users : ");
        for (int i = 0; i < usersTagged.size(); i++) {
            resultString.append(usersTagged.get(i).getFirstName())
                    .append(" ")
                    .append(usersTagged.get(i).getLastName());

            if(i < usersTagged.size()-1)
                resultString.append(", ");
            else
                resultString.append(".");
        }
        return resultString.toString();
    }
}
