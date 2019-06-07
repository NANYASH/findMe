package com.findMe.service;

import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.viewData.PostFilterData;
import com.findMe.model.viewData.PostParametersData;

import java.util.List;

public interface PostService {
    Post create(PostParametersData postParametersData) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError, BadRequestException;
    List<Post> getByPage(PostFilterData postFilterData) throws InternalServerError;
    List<Post> getByFriendsPages(Long userId, Integer offset) throws InternalServerError;
}
