package com.findMe.dao;

import com.findMe.model.User;

public interface UserDAO {
    User create(User user);
    User update(User user);
    void delete(Long id);
    User findById(Long id);
}
