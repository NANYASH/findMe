package com.findMe.service.impl;


import com.findMe.dao.PostDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.User;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostDAO postDAO;
    private RelationshipDAO relationshipDAO;
    private UserDAO userDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.postDAO = postDAO;
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }


    @Override
    public Post addPost(Post post, Long userPageId, Long[] usersTaggedIds) throws InternalServerError, BadRequestException {
        if (usersTaggedIds.length != 0)
            post.setUsersTagged(getUsersTagged(usersTaggedIds));

        if (post.getUserPosted().getId().equals(userPageId)) {
            post.setUserPagePosted(post.getUserPosted());
            post.setDatePosted(LocalDate.now());
            return postDAO.save(post);
        } else {

            Relationship relationship = relationshipDAO.getRelationship(userPageId, post.getUserPosted().getId());
            if (relationship != null && relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED)) {
                post.setUserPagePosted(userDAO.findById(userPageId));
                post.setDatePosted(LocalDate.now());
                return postDAO.save(post);
            }
        }
        throw new BadRequestException("Users are not friends. Action cannot be performed.");
    }


    @Override
    public List<Post> findByUserId(Long id) throws InternalServerError {
        return postDAO.findByUserId(id);
    }

    @Override
    public List<Post> findByUserPageId(Long id) throws InternalServerError {
        return postDAO.findByUserPageId(id);
    }

    @Override
    public List<Post> findByUserPageIdWithoutOwner(Long id) throws InternalServerError {
        return postDAO.findByUserPageIdWithoutOwner(id);
    }

    private List<User> getUsersTagged(Long[] usersTaggedIds) throws InternalServerError, BadRequestException {
        List<User> usersTagged = new ArrayList<>();

        for (Long userTagged : usersTaggedIds){
            User userFound = userDAO.findById(userTagged);
            if (userFound == null)
                throw new BadRequestException("User with id "+userTagged+" does not exist.");
            usersTagged.add(userFound);
        }
        return usersTagged;
    }


}
