package com.allenanker.flashnews.async;

import com.alibaba.fastjson.JSON;
import com.allenanker.flashnews.service.JedisService;
import com.allenanker.flashnews.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> handlersMap = new HashMap<>();

    @Autowired
    private JedisService jedisService;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> handlerBeans = applicationContext.getBeansOfType(EventHandler.class);
        if (handlerBeans != null) {
            for (Map.Entry<String, EventHandler> entry : handlerBeans.entrySet()) {
                List<EventType> supportEvents = entry.getValue().getSupportedEventTypes();
                for (EventType eventType : supportEvents) {
                    if (!handlersMap.containsKey(eventType)) {
                        handlersMap.put(eventType, new ArrayList<>());
                    }
                    handlersMap.get(eventType).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(() -> {
            while (true) {
                String eventQueueKey = RedisKeyUtil.getEventQueueKey();
                List<String> events = jedisService.brpop(0, eventQueueKey);
                for (String eventStr : events) {
                    if (eventStr.equals(eventQueueKey)) {
                        continue;
                    }
                    EventModel eventModel = JSON.parseObject(eventStr, EventModel.class);
                    if (!handlersMap.containsKey(eventModel.getEventType())) {
                        logger.error("Unsupported Event.");
                    }
                    for (EventHandler handler : handlersMap.get(eventModel.getEventType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
