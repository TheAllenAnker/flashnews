package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.model.HostHolder;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.service.QiniuService;
import com.allenanker.flashnews.service.UserService;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Map;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Map<String, Object> map) {
        News news = newsService.getById(newsId);
        if (news != null) {

        }
        map.put("news", news);
        User owner = userService.getUser(news.getUserId());
        if (owner != null) {
            map.put("owner", owner);
        }

        return "detail";
    }

    @RequestMapping(path = "/uploadImage/", method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return FlashNewsUtil.getJSONString(1, "Upload Failed");
            }

            return fileUrl;
        } catch (Exception e) {
            logger.error("Upload Image Failed: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Upload Failed");
        }
    }

    @RequestMapping(path = "/image", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName, HttpServletResponse response) {
        response.setContentType("image");
        try {
            StreamUtils.copy(new FileInputStream(new File(FlashNewsUtil.UPLOAD_IMAGE_DIR + imageName)),
                    response.getOutputStream());
        } catch (Exception e) {
            logger.error("Image Loading Error: " + e.getMessage());

        }
    }

    @RequestMapping(path = "/user/addNews/", method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image, @RequestParam("title") String title,
                        @RequestParam("link") String link) {
        try {
            News news = new News();
            news.setImage(image);
            news.setTitle(title);
            news.setLink(link);
            news.setCreatedDate(new Date());
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                // anonymous news
                news.setId(1);
            }
            newsService.addNews(news);

            return FlashNewsUtil.getJSONString(0, "Add news succeeded.");
        } catch (Exception e) {
            logger.error("Add News Error: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Add News Error");
        }
    }
}
