package org.pp.concurrent.pattern.eventdriver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncEventDispatcher implements DynamicRouter<Event> {
    /**
     * 创建一个线程安全的容器
     */
    private final Map<Class<? extends Event>, AsyncChannel> routerTable;

    public AsyncEventDispatcher() {
        this.routerTable = new ConcurrentHashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {
        if (!(channel instanceof AsyncChannel)) {
            throw new IllegalArgumentException("The Channel must be AsyncChannel Type.");
        }
        this.routerTable.put(messageType, (AsyncChannel) channel);
    }

    @Override
    public void dispatch(Event message) {
        if (this.routerTable.containsKey(message)) {
            this.routerTable.get(message.getType()).dispatch(message);
        } else {
            throw new IllegalArgumentException("Can't match the channel for [" + message.getType() + "] type.");
        }
    }

    /**
     * 关闭所有的Channel以释放资源
     */
    public void shutdown() {
        this.routerTable.values().forEach(AsyncChannel::stop);
    }

}
