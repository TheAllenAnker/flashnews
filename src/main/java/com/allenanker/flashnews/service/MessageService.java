package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.MessageDAO;
import com.allenanker.flashnews.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getMessagesByConvId(String convId, int offset, int limit) {
        return messageDAO.selectMessagesByConvId(convId, offset, limit);
    }

    public List<Message> getConversationByUserId(int userId, int offset, int limit) {
        return messageDAO.selectConversationList(userId, offset, limit);
    }
}
