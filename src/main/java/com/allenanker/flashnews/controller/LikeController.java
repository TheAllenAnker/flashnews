package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.async.EventModel;
import com.allenanker.flashnews.async.EventProducer;
import com.allenanker.flashnews.async.EventType;
import com.allenanker.flashnews.model.EntityType;
import com.allenanker.flashnews.model.HostHolder;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.service.LikeService;
import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return FlashNewsUtil.getJSONString(1, "You Must Login First.");
        }
        int userId = user.getId();
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikesCount(newsId, (int) likeCount);

        News news = newsService.getById(newsId);
        eventProducer.sendEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.ENTITY_NEWS).setEntityId(newsId).setEntityOwnerId(news.getUserId()));

        return FlashNewsUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return FlashNewsUtil.getJSONString(1, "You Must Login First.");
        }
        int userId = user.getId();
        long dislikeCount = likeService.dislike(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikesCount(newsId, (int) dislikeCount);
        return FlashNewsUtil.getJSONString(0, String.valueOf(dislikeCount));
    }
}
