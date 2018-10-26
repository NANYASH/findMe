package com.findMe.dao;


import com.findMe.model.Post;

public interface PostDAO {
    Post create(Post post);
    Post update(Post post);
    void delete(Long id);
    Post findById(Long id);

}
