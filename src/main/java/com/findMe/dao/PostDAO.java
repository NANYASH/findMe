package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.User;
import com.findMe.model.viewData.PostFilterData;

import java.util.List;

public interface PostDAO {
    Post save(Post post) throws InternalServerError;

    Post update(Post post) throws InternalServerError;

    List<Post> findPosts(PostFilterData postFilterData) throws InternalServerError;

    List<Post> findNews(Long userId, Integer offset) throws InternalServerError;

    List<User> findUsersTagged(Long userPostedId, Long[] usersTaggedIds) throws InternalServerError;
}
