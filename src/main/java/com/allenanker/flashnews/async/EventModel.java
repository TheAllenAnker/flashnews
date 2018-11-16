package com.allenanker.flashnews.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType eventType;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;
    private Map<String, String> paramsInfo;

    public EventModel(EventType eventType) {
        this.eventType = eventType;
        paramsInfo = new HashMap<>();
    }

    public EventModel() {
        paramsInfo = new HashMap<>();
    }

    public String getParam(String key) {
        return paramsInfo.get(key);
    }

    public void setParam(String key, String value) {
        paramsInfo.put(key, value);
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getParamsInfo() {
        return paramsInfo;
    }

    public EventModel setParamsInfo(Map<String, String> paramsInfo) {
        this.paramsInfo = paramsInfo;
        return this;
    }
}
