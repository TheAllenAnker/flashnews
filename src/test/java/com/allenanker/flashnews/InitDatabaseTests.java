package com.allenanker.flashnews;

import com.allenanker.flashnews.dao.LoginTicketDAO;
import com.allenanker.flashnews.dao.NewsDAO;
import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.LoginTicket;
import com.allenanker.flashnews.model.News;
import com.allenanker.flashnews.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
}
