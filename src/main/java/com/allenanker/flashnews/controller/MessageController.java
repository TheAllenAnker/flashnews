package com.allenanker.flashnews.controller;

import com.allenanker.flashnews.model.EntityType;
import com.allenanker.flashnews.model.Message;
import com.allenanker.flashnews.service.MessageService;
import com.allenanker.flashnews.util.FlashNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private MessageService messageService;

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
}
