package org.pp.concurrent.pattern.eventdriver;

public class Event implements Message{
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
