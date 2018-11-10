package com.allenanker.flashnews.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping({"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello, Spring!";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "Allen Anker") String key) {
        return String.format("GroupId: {%s}, UserId: {%d}, Type: {%d}, Key: {%s}", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/templates"})
    public String news(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("name", "Allen Anker");
        return "news";
    }

    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code) {
        RedirectView redirectView = new RedirectView("/", true);
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }

        return redirectView;
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "Error: " + e.getMessage();
    }
}