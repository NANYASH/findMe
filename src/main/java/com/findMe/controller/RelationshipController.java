package com.findMe.controller;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.exception.UnauthorizedException;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.service.RelationshipService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.convertRelationshipStatus;
import static com.findMe.util.Util.validateLogIn;

@Controller
public class RelationshipController {

    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseEntity addRelationship(HttpSession session, @RequestParam String userToId) {
        try {
            relationshipService.addRelationship(validateLogIn(session).getId(), convertId(userToId));
            Logger.getLogger("rootLogger").info("Relationship added");
            return new ResponseEntity("Request is sent.", HttpStatus.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("UnauthorizedException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").fatal("InternalServerError: "+e.getMessage());
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseEntity updateRelationship(HttpSession session, @RequestParam String userFromId, @RequestParam String status) {
        RelationshipStatus relationshipStatus;
        Long userSessionId;
        try {
            relationshipStatus = convertRelationshipStatus(status);
            userSessionId = validateLogIn(session).getId();
            if (relationshipStatus.equals(RelationshipStatus.CANCELED))
                relationshipService.updateRelationship(userSessionId, convertId(userFromId), relationshipStatus);
            else
                relationshipService.updateRelationship(convertId(userFromId), userSessionId, relationshipStatus);
            Logger.getLogger("rootLogger").info("Relationship updated");
            return new ResponseEntity("Relationship status is changed to" + status.toString(), HttpStatus.OK);
        } catch (UnauthorizedException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("UnauthorizedException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (BadRequestException e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").warn("BadRequestException: "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            Logger.getLogger("rootLogger").fatal("InternalServerError: "+e.getMessage());
            return new ResponseEntity("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
