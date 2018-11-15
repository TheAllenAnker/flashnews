package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.model.HostHolder;
import com.allenanker.flashnews.model.Message;
import com.allenanker.flashnews.model.User;
import com.allenanker.flashnews.model.ViewObject;
import com.allenanker.flashnews.service.MessageService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setFromId(fromId);
            message.setToId(toId);
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d",
                    toId, fromId));
            messageService.addMessage(message);
        } catch (Exception e) {
            logger.error("Commit Comment Error: " + e.getMessage());
            return FlashNewsUtil.getJSONString(1, "Add Message Failed.");
        }

        return FlashNewsUtil.getJSONString(0, "Add Message Succeeded.");
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String msgDetail(Map<String, Object> map, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> convMessages = messageService.getMessagesByConvId(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message : convMessages) {
                ViewObject msgVO = new ViewObject();
                msgVO.set("message", message);
                User user = userService.getUser(message.getFromId());
                if (user != null) {
                    msgVO.set("headUrl", user.getHeadUrl());
                    msgVO.set("userId", user.getId());
                    messages.add(msgVO);
                }
            }
            map.put("messages", messages);
        } catch (Exception e) {
            logger.error("Get Conversation Messages Failed.");
        }

        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Map<String, Object> map) {
        try {
            int loginedUserId = hostHolder.getUser().getId();
            // get the lastest conversation state with each user
            List<Message> userMessages = messageService.getConversationByUserId(loginedUserId, 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message message : userMessages) {
                ViewObject messageVO = new ViewObject();
                messageVO.set("conversation", message);
                messageVO.set("unreadCount", 0);
                int targetId = message.getFromId() == loginedUserId ? message.getToId() : message.getFromId();
                User user = userService.getUser(targetId);
                if (user != null) {
                    messageVO.set("userId", user.getId());
                    messageVO.set("headUrl", user.getHeadUrl());
                    messageVO.set("userName", user.getName());
                }
                conversations.add(messageVO);
            }
            map.put("conversations", conversations);
        } catch (Exception e) {
            logger.error("Get Conversation List Error: " + e.getMessage());
        }
        return "letter";
    }
}
