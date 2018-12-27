package com.findMe.util;


import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;

public class Util {
    public static Long convertId(String id) throws BadRequestException {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new BadRequestException("Cannot be converted to long type");
        }
    }

    public static void validateRelationshipStatus(RelationshipStatus currentStatus, RelationshipStatus newStatus) throws BadRequestException {
        if (currentStatus == null)
            throw new BadRequestException("Users don't have relationship.");
        if (currentStatus == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.ACCEPTED)
            return;
        if (currentStatus == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.REJECTED)
            return;
        if (currentStatus == RelationshipStatus.REJECTED && newStatus == RelationshipStatus.REQUESTED)
            return;
        throw new BadRequestException("Status cannot be changed.");
    }

    public static void validateRelationshipStatus(RelationshipStatus currentStatus) throws BadRequestException {
        if (currentStatus == null)
            throw new BadRequestException("Users don't have relationship.");
        if (currentStatus != RelationshipStatus.ACCEPTED)
            throw new BadRequestException("Users are not friends.");
    }
}
