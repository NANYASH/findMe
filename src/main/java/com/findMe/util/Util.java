package com.findMe.util;


import com.findMe.exception.BadRequestException;

public class Util {
    public static Long convertId(String id) throws BadRequestException{
        try{
            return Long.valueOf(id);
        }catch (NumberFormatException e){
            e.printStackTrace();
            throw new BadRequestException("Cannot be converted to long type");
        }
    }
}
