package com.findMe.controller;

import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import com.findMe.service.FriendsService;
import com.findMe.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


import java.util.List;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.convertRelationshipStatus;

@Controller
public class FriendsController {
    private FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    /*@RequestMapping(path = "/getRequestedFrom", method = RequestMethod.GET)
    public List<User> findRequestedFrom(HttpSession session,@RequestParam Long userId, @RequestParam RelationshipStatus status){


    }*/

    /*@RequestMapping(path = "/getRequestedTo", method = RequestMethod.GET)
    public List<User> findRequestedTo(HttpSession session,@RequestParam Long userId, @RequestParam RelationshipStatus status) {

    }*/

    @RequestMapping(path = "/addRelationship", method = RequestMethod.POST)
    public ResponseEntity addRelationship(HttpSession session ,@RequestParam String userToId){
        Long userFromId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity<>("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.addRelationship(userFromId,convertId(userToId));
            return new ResponseEntity<>("Request is sent.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @RequestMapping(path = "/deleteRelationship", method = RequestMethod.POST)
    public ResponseEntity deleteRelationship(HttpSession session,@RequestParam String userToId){
        Long userFromId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity<>("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.deleteRelationship(userFromId,convertId(userToId));
            return new ResponseEntity<>("User is deleted from friends.", HttpStatus.OK);
        } catch (BadRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/updateRelationship", method = RequestMethod.POST)
    public ResponseEntity updateRelationship(HttpSession session ,@RequestParam String userFromId,@RequestParam String status){
        Long userToId = (Long) session.getAttribute("id");
        if (userFromId == null)
            return new ResponseEntity<>("User should be logged in.", HttpStatus.UNAUTHORIZED);

        try {
            friendsService.updateRelationship(convertId(userFromId),userToId,convertRelationshipStatus(status));
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
