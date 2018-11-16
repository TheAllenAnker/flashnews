package com.allenanker.flashnews.async;

import com.alibaba.fastjson.JSON;
import com.allenanker.flashnews.service.JedisService;
import com.allenanker.flashnews.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private JedisService jedisService;

    public boolean sendEvent(EventModel eventModel) {
        try {
            String objectInJson = JSON.toJSONString(eventModel);
            String eventQueueKey = RedisKeyUtil.getEventQueueKey();
            jedisService.lpush(eventQueueKey, objectInJson);
            return true;
        } catch (Exception e) {
            logger.error("EventProducer: push event error : " + e.getMessage());
            return false;
        }
    }
}
