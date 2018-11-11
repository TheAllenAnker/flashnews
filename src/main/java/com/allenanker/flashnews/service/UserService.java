package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
