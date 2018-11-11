package com.allenanker.flashnews;

import com.allenanker.flashnews.dao.NewsDAO;
import com.allenanker.flashnews.dao.UserDAO;
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

	@Test
	public void test1() {
        User user = new User();
        user.setHeadUrl("");
        user.setName("");
        user.setPassword("");
        user.setSalt("");
        user.setId(1);
        userDAO.addUser(user);
        user.setPassword("newPassword");
        userDAO.updatePassword(user);
        Assert.assertEquals("newPassword", userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));

        News news = new News();
        news.setUserId(1);
        news.setTitle("News Title");
        news.setLink("https://www.baidu.com");
        news.setImage("https://www.image.com");
        news.setCreatedDate(new Date());
        userDAO.addUser(user);
        newsDAO.addNews(news);
	}

}
