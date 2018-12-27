package com.findMe.controller;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static com.findMe.util.Util.convertRelationshipStatus;

@Controller
public class FriendsController {
    private FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @RequestMapping(path = "/addRelationship", method = RequestMethod.PUT)
    public ResponseEntity addRelationship(HttpSession session ,@RequestParam Long userFromId,@RequestParam Long userToId){
        if (session.getAttribute("user") == null)
            return new ResponseEntity<>("User should be logged in", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.addRelationship(userFromId,userToId);
            return new ResponseEntity<>("Request is sent.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.DELETE)
    public ResponseEntity deleteRelationship(HttpSession session ,@RequestParam Long userFromId,@RequestParam Long userToId){
        if (session.getAttribute("user") == null)
            return new ResponseEntity<>("User should be logged in", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.deleteRelationship(userFromId,userToId);
            return new ResponseEntity<>("User is deleted from friends.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.PUT)
    public ResponseEntity updateRelationship(HttpSession session ,@RequestParam Long userFromId,@RequestParam Long userToId,@RequestParam String status){
        if (session.getAttribute("user") == null)
            return new ResponseEntity<>("User should be logged in", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.updateRelationship(userFromId,userToId,convertRelationshipStatus(status));
            return new ResponseEntity<>("Relationship status is changed to"+status.toString(), HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
