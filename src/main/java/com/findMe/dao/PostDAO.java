package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;

public interface PostDAO {
    Post save(Post post) throws InternalServerError;
    Post update(Post post) throws InternalServerError;
    void delete(Long id) throws InternalServerError;
    Post findById(Long id) throws InternalServerError;

}
