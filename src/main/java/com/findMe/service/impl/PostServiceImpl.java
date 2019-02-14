package com.findMe.service.impl;


import com.findMe.dao.PostDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.dao.UserDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.validateData.PostParametersData;
import com.findMe.model.validateData.PostValidatorRequestData;
import com.findMe.service.PostService;
import com.findMe.validator.postValidator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

import static com.findMe.util.Util.validateIds;

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
    public Post addPost(PostParametersData postParametersData) throws InternalServerError, BadRequestException {
        Relationship relationship = null;
        Post post = buildPost(postParametersData);

        Long[] usersTaggedIds = validateIds(postParametersData.getUsersTagged());

        if (usersTaggedIds.length != 0)
            post.setUsersTagged(postDAO.findUsersTagged(usersTaggedIds));
        if (postParametersData.getUserPosted().getId().equals(postParametersData.getUserPageId())) {
            post.setUserPagePosted(postParametersData.getUserPosted());
        } else {
            relationship = relationshipDAO.getRelationship(postParametersData.getUserPageId(), postParametersData.getUserPosted().getId());
            post.setUserPagePosted(userDAO.findById(postParametersData.getUserPageId()));
        }

        postValidator.validatePost(new PostValidatorRequestData(post,usersTaggedIds,relationship));
        post.setDatePosted(LocalDate.now());
        return postDAO.save(post);
    }

    @Override
    public List<Post> findPosts(Long userPageId, String userPostedId, String byFriends) throws InternalServerError {
        return postDAO.findPosts(userPageId,userPostedId,byFriends);
    }

    private Post buildPost(PostParametersData postParametersData){
        Post post = new Post();
        post.setUserPosted(postParametersData.getUserPosted());
        post.setLocation(postParametersData.getLocation());
        post.setText(postParametersData.getText());
        return post;
    }

}
