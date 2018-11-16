package com.allenanker.flashnews.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportedEventTypes();
}
