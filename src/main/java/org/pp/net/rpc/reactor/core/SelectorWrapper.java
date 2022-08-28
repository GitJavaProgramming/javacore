package org.pp.net.rpc.reactor.core;

import java.io.IOException;
import java.nio.channels.Selector;

public class SelectorWrapper {

    private static final SelectorWrapper instance = new SelectorWrapper();
    private Selector[] selectors;
    private Selector currSelector;
    private int curr = 0;

    private SelectorWrapper() {
    }

    public static SelectorWrapper getInstance(int count) {
        instance.selectors = instance.configSelectors(count);
        return instance;
    }

    public Selector[] configSelectors(int count) {
        if (selectors == null) {
            selectors = new Selector[count];
            try {
                for (int i = 0; i < selectors.length; i++) {
                    selectors[i] = Selector.open();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return selectors;
    }

    public synchronized Selector getCurrSelector() {
        currSelector = selectors[curr++ % selectors.length];
        curr %= selectors.length;
        if (curr == selectors.length) {
            curr = 0;
        }
        return currSelector;
    }

    public synchronized Selector[] getSelectors() {
        return selectors;
    }
}
