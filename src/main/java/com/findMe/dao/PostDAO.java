package com.findMe.dao;


import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.User;
import com.findMe.model.viewData.PostFilterData;

import java.util.List;

public interface PostDAO {
    Post create(Post post) throws InternalServerError;

    Post update(Post post) throws InternalServerError;

    void delete(Long id) throws InternalServerError;

    List<Post> getPosts(PostFilterData postFilterData) throws InternalServerError;

    List<Post> getNews(Long userId, Integer offset) throws InternalServerError;

    List<User> getTaggedUsers(Long userPostedId, Long[] usersTaggedIds) throws InternalServerError;
}
