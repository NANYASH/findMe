package com.findMe.service.impl;


import com.findMe.dao.PostDAO;
import com.findMe.dao.RelationshipDAO;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipStatus;
import com.findMe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostDAO postDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, RelationshipDAO relationshipDAO) {
        this.postDAO = postDAO;
        this.relationshipDAO = relationshipDAO;
    }

    @Override
    public Post addPost(Post post) throws InternalServerError, BadRequestException {
        if (post.getUserPagePosted().equals(post.getUserPosted()))
            return postDAO.save(post);
        else {
            Relationship relationship = relationshipDAO.getRelationship(post.getUserPagePosted().getId(), post.getUserPosted().getId());
            if (relationship != null && relationship.getRelationshipStatus().equals(RelationshipStatus.ACCEPTED))
                return postDAO.save(post);
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
}
