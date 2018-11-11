package com.allenanker.flashnews;

import com.allenanker.flashnews.dao.UserDAO;
import com.allenanker.flashnews.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

	@Test
	public void contextLoads() {
        User user = new User();
        user.setHeadUrl("");
        user.setName("");
        user.setPassword("");
        user.setSalt("");
        userDAO.addUser(user);
	}

}
