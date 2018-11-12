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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
            defaultValue = "0") int remember, HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remember > 0) {
                    cookie.setMaxAge(3600*1000*24);
                }
                response.addCookie(cookie);
                return FlashNewsUtil.getJSONString(0, "Registration Succeeded.");
            } else {
                return FlashNewsUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("Registration Failed: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Registration Failed");
        }
    }

    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Map<String, Object> model, @RequestParam("username") String username,
                      @RequestParam("password") String password, @RequestParam(value = "remember",
            defaultValue = "0") int remember, HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remember > 0) {
                    cookie.setMaxAge(3600*1000*24);
                }
                response.addCookie(cookie);
                return FlashNewsUtil.getJSONString(0, "Login Succeeded.");
            } else {
                return FlashNewsUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("Login Failed: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Login Failed");
        }
    }
}
