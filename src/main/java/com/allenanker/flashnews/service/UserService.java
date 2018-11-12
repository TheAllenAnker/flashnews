package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.util.FlashNewsUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserDAO userDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNullOrEmpty(username)) {
            map.put("msgusername", "Username cannot be empty.");
        }
        if (StringUtils.isNullOrEmpty(password)) {
            map.put("msgpassword", "Password cannot be empty.");
        }

        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msgname", "Username has been taken.");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(FlashNewsUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);

        return map;
    }
}
