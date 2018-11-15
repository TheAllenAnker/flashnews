package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.model.*;
import com.allenanker.flashnews.service.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId, @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);

            int oldCommentCount = commentService.getCommentsCount(newsId, EntityType.ENTITY_NEWS);
            newsService.updateCommentsCount(newsId, oldCommentCount + 1);
        } catch (Exception e) {
            logger.error("Commit Comment Error: " + e.getMessage());
        }

        return "redirect:/news/" + String.valueOf(newsId);
    }

    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Map<String, Object> map) {
        News news = newsService.getById(newsId);
        if (news != null) {
            List<Comment> comments = commentService.getCommentsByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<>();
            for (Comment comment : comments) {
                ViewObject commentVo = new ViewObject();
                commentVo.set("comment", comment);
                commentVo.set("user", userService.getUser(comment.getUserId()));
                commentVOs.add(commentVo);
            }
            map.put("commentvos", commentVOs);
        }
        int likeStatus = 0;
        if (hostHolder.getUser() != null) {
            likeStatus = likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_NEWS, newsId);
        }

        map.put("news", news);
        map.put("like", likeStatus);
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
