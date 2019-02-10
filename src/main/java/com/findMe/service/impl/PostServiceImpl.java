package com.findMe.service.impl;


import com.findMe.dao.PostDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.service.PostService;
import com.findMe.validator.postValidator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostDAO postDAO;
    private RelationshipDAO relationshipDAO;
    private UserDAO userDAO;
    private PostValidator postValidator;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, RelationshipDAO relationshipDAO, UserDAO userDAO, PostValidator postValidator) {
        this.postDAO = postDAO;
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
        this.postValidator = postValidator;
    }

    @Override
    public Post addPost(Post post, Long userPageId, Long[] usersTaggedIds) throws InternalServerError, BadRequestException {
        Relationship relationship = null;

        if (usersTaggedIds.length != 0)
            post.setUsersTagged(postDAO.findUsersTagged(usersTaggedIds));

        if (post.getUserPosted().getId().equals(userPageId)) {
            post.setUserPagePosted(post.getUserPosted());
        } else {
            relationship = relationshipDAO.getRelationship(userPageId, post.getUserPosted().getId());
            post.setUserPagePosted(userDAO.findById(userPageId));
        }
        postValidator.validatePost(post, usersTaggedIds, relationship);
        post.setDatePosted(LocalDate.now());
        return postDAO.save(post);
    }

    @Override
    public List<Post> findPosts(Long userPageId, String friendId, String byFriends, String byOwner) throws InternalServerError {
        return postDAO.findPosts(userPageId,friendId,byFriends,byOwner);
    }

}
