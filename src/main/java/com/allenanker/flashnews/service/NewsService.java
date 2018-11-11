package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.NewsDAO;
import com.allenanker.flashnews.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLastestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }
}
