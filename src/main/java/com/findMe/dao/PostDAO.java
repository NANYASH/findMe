package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;

import java.util.List;

public interface PostDAO {
    Post save(Post post) throws InternalServerError;

    Post update(Post post) throws InternalServerError;

    List<Post> findByUserId(Long id) throws InternalServerError;

    List<Post> findByUserPageId(Long id) throws InternalServerError;

    List<Post> findByUserPageIdWithoutOwner(Long id) throws InternalServerError;
}
