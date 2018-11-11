package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"})
    public String index(HttpSession session) {
        return "home";
    }
}
