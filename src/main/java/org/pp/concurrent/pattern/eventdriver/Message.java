package org.pp.concurrent.pattern.eventdriver;

public interface Message {

    Class<? extends Message> getType();
}
