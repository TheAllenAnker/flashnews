package com.allenanker.flashnews.async;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Override
    public void doHandle(EventModel eventModel) {
        System.out.println("Liked");
    }

    @Override
    public List<EventType> getSupportedEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
