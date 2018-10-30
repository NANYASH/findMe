package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.model.Post;

public class PostDAOImpl extends GenericDAO<Post> implements PostDAO{
    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public Post findById(Long id) {
        return super.findById(id);
    }

    @Override
    Class<Post> getEntityClass() {
        return Post.class;
    }
}
