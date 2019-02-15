package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.User;

import java.util.List;

public interface PostDAO {
    Post save(Post post) throws InternalServerError;

    Post update(Post post) throws InternalServerError;

    List<Post> findPosts(Long userPageId, String userPostedId, String byFriends) throws InternalServerError;

    List<User> findUsersTagged(Long userPostedId, Long[] usersTaggedIds) throws InternalServerError;
}
