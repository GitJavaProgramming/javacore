package org.pp.concurrent.pattern.eventdriver;

import java.util.HashMap;
import java.util.Map;

public class SyncEventDispatcher implements DynamicRouter<Message> {

    private final Map<Class<? extends Message>, Channel> routerTable;

    public SyncEventDispatcher() {
        this.routerTable = new HashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Message> messageType, Channel<? extends Message> channel) {
        this.routerTable.put(messageType, channel);
    }

    @Override
    public void dispatch(Message message) {
        if (this.routerTable.containsKey(message.getType())) {
            this.routerTable.get(message.getType()).dispatch(message);
        } else {
            throw new IllegalArgumentException("Can't match the channel for [" + message.getType() + "] type.");
        }
    }
}
