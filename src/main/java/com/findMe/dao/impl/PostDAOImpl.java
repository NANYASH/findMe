package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public class PostDAOImpl extends GenericDAO<Post> implements PostDAO{
    @Override
    public void delete(Long id) throws InternalServerError {
        super.remove(id);
    }

    @Override
    public Post findById(Long id) throws InternalServerError {
        return super.findById(id);
    }

    @Override
    Class<Post> getEntityClass() {
        return Post.class;
    }
}
