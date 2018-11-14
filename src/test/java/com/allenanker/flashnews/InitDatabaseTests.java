package com.allenanker.flashnews;

import com.allenanker.flashnews.dao.CommentDAO;
import com.allenanker.flashnews.dao.LoginTicketDAO;
import com.allenanker.flashnews.dao.NewsDAO;
import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.*;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    CommentDAO commentDAO;

	@Test
	public void test1() {
        User user = new User();
        user.setHeadUrl("hearUrl");
        user.setName("name");
        user.setPassword("password");
        user.setSalt("salt");
        user.setId(1);
        userDAO.addUser(user);
        user.setPassword("newPassword");
        userDAO.updatePassword(user);
        Assert.assertEquals("newPassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        News news = new News();
        news.setUserId(2);
        news.setTitle("News Title");
        news.setLink("https://www.baidu.com");
        news.setImage("https://www.image.com");
        news.setLikeCount(2);
        news.setCommentCount(3);
        news.setCreatedDate(new Date());
        userDAO.addUser(user);
        newsDAO.addNews(news);
	}

	@Test
	public void test2() {
        LoginTicket ticket = new LoginTicket();
        ticket.setTicket("ticket info");
        ticket.setUserId(4);
        ticket.setStatus(0);
        ticket.setExpired(new Date());
        loginTicketDAO.addTicket(ticket);
    }

    @Test
    public void addNewsTest3() {
        User user = new User();
        user.setHeadUrl("hearUrl");
        user.setName("name");
        user.setPassword("password");
        user.setSalt("salt");
        userDAO.addUser(user);
        for (int i = 1; i <= 10; i++) {
            News news = new News();
            news.setUserId(1);
            news.setTitle("News Title");
            news.setLink("https://www.baidu.com");
            news.setImage("https://www.image.com");
            news.setLikeCount(i);
            news.setCommentCount(i);
            news.setCreatedDate(new Date());
            newsDAO.addNews(news);
        }
    }

    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://localhost:8080/news/%d.html", i));
            newsDAO.addNews(news);

            // 给每个资讯插入3个评论
            for(int j = 0; j < 3; ++j) {
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("这里是一个评论啊！" + String.valueOf(j));
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                commentDAO.addComment(comment);
            }

            user.setPassword(FlashNewsUtil.MD5("newpassword" + user.getSalt()));
            userDAO.updatePassword(user);
        }

        Assert.assertEquals(FlashNewsUtil.MD5("newpassword" + userDAO.selectById(1).getSalt()),
                userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));
        Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());
        Assert.assertNotNull(commentDAO.selectByEntity(1, EntityType.ENTITY_NEWS).get(0));
    }
}
