package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.service.NewsService;
import com.allenanker.flashnews.service.UserService;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Map<String, Object> model, @RequestParam("username") String username,
                      @RequestParam("password") String password, @RequestParam(value = "remember",
            defaultValue = "0") int remember) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.isEmpty()) {
                return FlashNewsUtil.getJSONString(0, "Registration Succeeded.");
            } else {
                return FlashNewsUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("Registration Failed: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Registration Failed");
        }
    }
}
