package com.allenanker.flashnews;

import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.service.JedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTests {
    @Autowired
    private JedisService jedisService;

	@Test
	public void savaObjectTest() {
        User user = new User();
        user.setHeadUrl("http://image.nowcoder.com/head/100t.png");
        user.setName("user1");
        user.setPassword("pwd");
        user.setSalt("salt");

        jedisService.setObject("user1", user);
        User user1 = jedisService.getObject("user1", User.class);
        System.out.println(user1);
	}

}
