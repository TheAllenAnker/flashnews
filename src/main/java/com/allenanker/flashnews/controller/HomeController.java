package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.dao.ViewObject;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"})
    public String index(Map<String, Object> model) {
        List<News> newsList = newsService.getLastestNews(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }

        model.put("vos", vos);
        return "home";
    }
}
