package com.findMe.service;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.viewData.PostFilterData;
import com.findMe.model.viewData.PostParametersData;

import java.util.List;

public interface PostService {
    Post addPost(PostParametersData postParametersData) throws InternalServerError, BadRequestException;
    List<Post> findPostsByPage(PostFilterData postFilterData) throws InternalServerError;
    List<Post> findPostsByFriendsPages(Long userId) throws InternalServerError;
}
