package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.model.Post;

public class PostDAOImpl extends GenericDAO<Post> implements PostDAO{
    @Override
    public void delete(Long id) {
        super.delete(Post.class,id);
    }

    @Override
    public Post findById(Long id) {
        return super.findById(Post.class,id);
    }
}
