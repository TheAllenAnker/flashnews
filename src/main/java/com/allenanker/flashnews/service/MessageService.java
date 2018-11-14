package com.allenanker.flashnews.service;

import com.allenanker.flashnews.dao.MessageDAO;
import com.allenanker.flashnews.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }
}
