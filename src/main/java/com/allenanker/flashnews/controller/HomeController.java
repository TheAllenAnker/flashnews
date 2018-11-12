package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.model.ViewObject;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Map<String, Object> model, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.put("vos", getNews(1, 0, 10));
        model.put("pop", pop);

        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLastestNews(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }

        return vos;
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Map<String, Object> model, @PathVariable("userId") int userId) {
        model.put("vos", getNews(userId, 0, 10));
        return "home";
    }
}
