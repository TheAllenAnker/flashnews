package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.LoginTicketDAO;
import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.LoginTicket;
import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.util.FlashNewsUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

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
        // after registering successfully
        // add the ticket to ticket database and return the ticket info to the client
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNullOrEmpty(username)) {
            map.put("msgusername", "Username cannot be empty.");
        }
        if (StringUtils.isNullOrEmpty(password)) {
            map.put("msgpassword", "Password cannot be empty.");
        }

        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msgname", "No such user.");
            return map;
        }
        if (!user.getPassword().equals(FlashNewsUtil.MD5(password + user.getSalt()))) {
            map.put("msgpassword", "Username or Password is wrong.");
            return map;
        }
        // add the ticket to ticket database and return the ticket info to the client
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDAO.addTicket(ticket);

        return ticket.getTicket();
    }
}
