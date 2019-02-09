package com.findMe.service;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.User;

import java.util.List;

public interface PostService {
    Post addPost(Post post, Long userPageId, Long[]  usersTaggedIds) throws InternalServerError, BadRequestException;
    List<Post> findByUserId(Long id) throws InternalServerError;
    List<Post> findByUserPageId(Long id) throws InternalServerError;
    List<Post> findByUserPageIdWithoutOwner(Long id) throws InternalServerError;
}
