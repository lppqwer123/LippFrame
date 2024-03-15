package com.first.frame.base;

public class EventMessage {
    public EventMessage(String event_action, Object data) {
        this.event_action = event_action;
        this.data = data;
    }
    public EventMessage() {
    }


    public String getEvent_action() {
        return event_action;
    }

    public void setEvent_action(String event_action) {
        this.event_action = event_action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private String event_action;
    private Object data;
}
